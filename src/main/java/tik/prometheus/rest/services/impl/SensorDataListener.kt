package tik.prometheus.rest.services.impl

import com.google.gson.JsonObject
import org.apache.avro.generic.GenericData
import org.apache.avro.util.Utf8
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.PartitionOffset
import org.springframework.kafka.annotation.TopicPartition
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import tik.prometheus.rest.MqttClientFactory
import tik.prometheus.rest.constants.ActuatorTaskType
import tik.prometheus.rest.models.ActuatorTask
import tik.prometheus.rest.repositories.ActuatorRepos
import tik.prometheus.rest.repositories.DecisionTreeRepos
import tik.prometheus.rest.repositories.SensorRecordRepos
import tik.prometheus.rest.services.ActuatorService

@Component
class SensorDataListener @Autowired constructor(
    val sensorRecordRepos: SensorRecordRepos,
    val actuatorRepos: ActuatorRepos,
    val mqttClientFactory: MqttClientFactory,
    val decisionTreeRepos: DecisionTreeRepos,
    @Value("\${decisiontree.url}")
    val decisionTreeUrl: String
) {

    @KafkaListener(
        id = "class-level",
        topicPartitions = [
            TopicPartition(
                topic = "ora-SENSOR_RECORD-jdbc-01",
                partitionOffsets = [PartitionOffset(partition = "0", initialOffset = "-1")]
            ),
            TopicPartition(
                topic = "ora-SENSOR_RECORD-jdbc-02",
                partitionOffsets = [PartitionOffset(partition = "0", initialOffset = "-1")]
            )
        ]
    )
    fun listenDTO(
        @Header(name = KafkaHeaders.RECEIVED_MESSAGE_KEY, required = false) key: String,
        @Payload message: GenericData.Record
    ) {
        val recordId = key.toFloat().toLong()

        val sensorData: Float
        try {
            sensorData = (message["SENSOR_DATA"] as Utf8).toString().toFloat()
        } catch (e: Exception) {
            return
        }
        println("Kafka received record $recordId $sensorData")
        val tasks = actuatorRepos.findActuatorTaskBySensorRecord(recordId)
        tasks.forEach { resolveTask(it, sensorData) }
    }

    private fun resolveTask(task: ActuatorTask, sensorData: Float) {
        when (task.taskType) {
            ActuatorTaskType.DECISION -> handleDecisionTreeTask(task, sensorData)
            ActuatorTaskType.RANGE -> handleRangeTask(task, sensorData)
            else -> {}
        }
    }

    private fun handleRangeTask(task: ActuatorTask, sensorData: Float) {
        val isOuterOfRange = isOuterOfRangeSensorValue(task, sensorData)
        var msg: MqttMessage? = null
        println("${!isOuterOfRange} ${task.actuator?.isRunning != false}")
        if (isOuterOfRange && (task.actuator?.isRunning != true)) {
            msg = MqttMessage("1".toByteArray())
            task.actuator?.isRunning = true
        } else if (!isOuterOfRange && (task.actuator?.isRunning != false)) {
            msg = MqttMessage("0".toByteArray())
            task.actuator?.isRunning = false
        }
        sensMqttMessage(task, msg)
    }

    private fun handleDecisionTreeTask(task: ActuatorTask, sensorData: Float) {
        println("Handle Decision Tree")
        val restTemplate = RestTemplate()
        val res = restTemplate.getForEntity(decisionTreeUrl + "/decision", JsonObject::class.java, mapOf("sensor_data" to sensorData))
        println(res.body)
        var run = res.body!!["data"].asString == "N"
        var msg: MqttMessage? = null
        if (run && task.actuator?.isRunning != true) {
            msg = MqttMessage("1".toByteArray())
            task.actuator?.isRunning = true
        } else if (!run && task.actuator?.isRunning != false) {
            msg = MqttMessage("0".toByteArray())
            task.actuator?.isRunning = false
        }
        sensMqttMessage(task, msg)
    }

    private fun sensMqttMessage(task: ActuatorTask, msg: MqttMessage?) {
        if (msg != null) {
            actuatorRepos.saveAndFlush(task.actuator!!)
            val topic = ActuatorService.actuatorTopic(task.actuator!!)
            println("pub actuator $topic $msg")
            val client = mqttClientFactory.create()
            msg.qos = 1
            msg.isRetained = true
            client.connect()
            client.publish(topic, msg)
            client.disconnect()
            client.close()
        }
    }

    private fun isOuterOfRangeSensorValue(actuatorTask: ActuatorTask, sensorData: Float): Boolean {
        if (sensorData < (actuatorTask.startValue ?: Float.MIN_VALUE) || sensorData > (actuatorTask.limitValue ?: Float.MAX_VALUE)) {
            return true
        }
        return false
    }

}