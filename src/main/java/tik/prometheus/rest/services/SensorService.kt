package tik.prometheus.rest.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tik.prometheus.rest.dtos.SensorDTO
import tik.prometheus.rest.models.toSensorDTO
import tik.prometheus.rest.repositories.SensorRepos

@Service
class SensorService @Autowired constructor(
    private val sensorRepos: SensorRepos,
) {
    fun getSensors(): List<SensorDTO> {
        return sensorRepos.findAll().map { s -> s.toSensorDTO() }
    }
}