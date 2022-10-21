package tik.prometheus.rest.services.impl

import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import tik.prometheus.rest.Configurations
import tik.prometheus.rest.dtos.ActuatorDTO
import tik.prometheus.rest.dtos.ActuatorLiteDTO
import tik.prometheus.rest.dtos.toActuatorDTO
import tik.prometheus.rest.dtos.toActuatorLiteDTO
import tik.prometheus.rest.models.Actuator
import tik.prometheus.rest.repositories.ActuatorRepos
import tik.prometheus.rest.services.ActuatorService

@Service
class ActuatorServiceImpl @Autowired constructor(
    private val actuatorRepos: ActuatorRepos,
    private val configurations: Configurations
) : ActuatorService {

    override fun getActuators(greenhouseId: Long, pageable: Pageable): Page<ActuatorLiteDTO> {
        val pageEntity = actuatorRepos.findAllWithParams(greenhouseId, pageable)
        return pageEntity.map(Actuator::toActuatorLiteDTO)
    }

    override fun getActuator(id: Long): ActuatorDTO {
        return actuatorRepos.findById(id).map(Actuator::toActuatorDTO).orElseThrow { throw ResponseStatusException(HttpStatus.NOT_FOUND, "No result found") }
    }

    override fun patchActuator(id: Long, nextState: ActuatorDTO.ActuatorState) {
        val actuator = actuatorRepos.findById(id).orElseThrow {
            throw ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        actuator.isRunning = nextState.isRunning
        actuatorRepos.saveAndFlush(actuator);
        val url = "tcp://%s:%s".format(configurations.brokerHost, configurations.brokerPort)
        println(url)
        val client = MqttClient(url, configurations.mqttClientId)
        val options = MqttConnectOptions()
        options.isAutomaticReconnect = true
        options.isCleanSession = true
        options.connectionTimeout = 10
        val topic = ActuatorService.actuatorTopic(actuator)
        println(topic)
        val msg = MqttMessage(
            (if (actuator.isRunning) "1" else "0").toByteArray()
        )
        msg.qos = 1
        msg.isRetained = true
        client.connect()
        client.publish(topic, msg)
        client.disconnect()
        client.close()

    }

}