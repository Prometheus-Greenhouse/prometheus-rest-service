package tik.prometheus.rest.services.impl

import org.apache.avro.generic.GenericData
import org.apache.avro.util.Utf8
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.PartitionOffset
import org.springframework.kafka.annotation.TopicPartition
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import tik.prometheus.rest.MqttClientFactory
import tik.prometheus.rest.models.ActuatorTask
import tik.prometheus.rest.repositories.ActuatorRepos
import tik.prometheus.rest.repositories.SensorRecordRepos
import tik.prometheus.rest.services.ActuatorService

@Component
class SensorDataListener @Autowired constructor(
    val sensorRecordRepos: SensorRecordRepos,
    val actuatorRepos: ActuatorRepos,
    val mqttClientFactory: MqttClientFactory
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
        if (msg != null) {
            actuatorRepos.saveAndFlush(task.actuator!!)
            println("pub actuator $msg")
            val topic = ActuatorService.actuatorTopic(task.actuator!!)
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