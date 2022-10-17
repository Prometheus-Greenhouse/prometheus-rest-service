package tik.prometheus.rest.services.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import tik.prometheus.rest.dtos.SensorDTO
import tik.prometheus.rest.dtos.SensorLiteDTO
import tik.prometheus.rest.dtos.toSensorDTO
import tik.prometheus.rest.dtos.toSensorLiteDTO
import tik.prometheus.rest.models.Sensor
import tik.prometheus.rest.repositories.SensorRepos
import tik.prometheus.rest.services.SensorService

@Service
class SensorServiceImpl @Autowired constructor(
    private val sensorRepos: SensorRepos,
) : SensorService {
    override fun getSensors(pageable: Pageable): Page<SensorLiteDTO> {
        val pageEntity = sensorRepos.findAll(pageable)
        return pageEntity.map(Sensor::toSensorLiteDTO)
    }

    override fun getSensor(id: Long): SensorDTO {
        return sensorRepos.findById(id)
            .map(Sensor::toSensorDTO)
            .orElseThrow { throw ResponseStatusException(HttpStatus.NOT_FOUND, "No result found") }
    }
}

