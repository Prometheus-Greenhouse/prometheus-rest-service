package tik.prometheus.rest

import com.google.gson.Gson
import org.apache.commons.lang3.RandomStringUtils
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.hibernate.engine.jdbc.ClobProxy
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.stereotype.Service
import tik.prometheus.rest.constants.HumidityValue
import tik.prometheus.rest.constants.SensorType
import tik.prometheus.rest.models.*
import tik.prometheus.rest.repositories.*
import tik.prometheus.rest.services.SensorService

@Service
class OracleStartup @Autowired constructor(
    val sensorRepos: SensorRepos,
    val greenhouseRepos: GreenhouseRepos,
    val sensorRecordRepos: SensorRecordRepos,
    val farmRepos: FarmRepos,
    val sensorTypeMetadataRepos: SensorTypeMetadataRepos
) : InitializingBean {
    val log = LoggerFactory.getLogger(OracleStartup::class.java)

    override fun afterPropertiesSet() {

        if (farmRepos.findAll(Example.of(Farm(label = "sample"))).size == 0) {
            log.info("Run startup oracle script")
            createSampleData()
        }

        mockSensorData()

    }

    fun createSampleData() {
        val farm = Farm()
        farmRepos.save(farm)

        val gh = Greenhouse(farmId = farm.id)
        greenhouseRepos.save(gh)

        val sensor = Sensor(
            localId = "00:00:00:00:00/sample",
            address = "sample",
            label = "sample",
        )
        sensorRepos.save(sensor)

        sensor.allocation.add(
            SensorAllocation(
                greenhouseId = gh.id,
                sensorId = sensor.id
            )
        )
        sensorRepos.save(sensor)

        sensorRecordRepos.save(
            SensorRecord(
                greenhouseId = gh.id,
                sensorId = sensor.id,
                sensorData = "100"
            )
        )
    }

    fun mockSensorData() {
        log.info("mock sensor data")
        val t = sensorTypeMetadataRepos.findById(SensorType.TEMPERATURE);
//        sensorTypeMetadataRepos.save(
//            SensorTypeMetadata(
//                SensorType.HUMIDITY,
//                ClobProxy.generateProxy(Gson().toJson(HumidityValue()))
//            )
//        )
    }

    class MockSensorData(
        val sensorRepos: SensorRepos,
        val mqttClientFactory: MqttClientFactory
    ) : Runnable {

        override fun run() {
            val client = mqttClientFactory.create()
            client.connect()
            while (true) {
                val sensors = sensorRepos.findAll(Example.of(Sensor(label = "sample")))

                sensors.forEach {
                    val topic = SensorService.sensorTopic(it)
                    val msg = MqttMessage(
                        RandomStringUtils.randomNumeric(3).toByteArray()
                    )
                    msg.qos = 1
                    msg.isRetained = true
                    client.publish(topic, msg)
                }
                Thread.sleep(5000)
            }
        }

    }

}