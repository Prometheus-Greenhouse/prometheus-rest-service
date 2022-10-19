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
import tik.prometheus.rest.models.SensorAllocation
import tik.prometheus.rest.repositories.GreenhouseRepos
import tik.prometheus.rest.repositories.SensorRepos
import tik.prometheus.rest.services.SensorService

@Service
class SensorServiceImpl @Autowired constructor(
    private val repos: SensorRepos,
    private val greenhouseRepos: GreenhouseRepos,

    ) : SensorService {
    override fun getSensors(pageable: Pageable, greenhouseId: Long?): Page<SensorLiteDTO> {
        val pageEntity = repos.findAllWithParams(greenhouseId, pageable)
        return pageEntity.map(Sensor::toSensorLiteDTO)
    }

    override fun getSensor(id: Long): SensorDTO {
        return repos.findById(id)
            .map(Sensor::toSensorDTO)
            .orElseThrow { throw ResponseStatusException(HttpStatus.NOT_FOUND, "No result found") }
    }

    override fun updateSensor(sensorId: Long, sensorDto: SensorDTO): SensorDTO {
        val sensor = repos.findById(sensorId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }

        sensor.type = sensorDto.type
        sensor.localId = sensorDto.localId
        sensor.address = sensorDto.address
        sensor.label = sensorDto.label
        sensor.unit = sensorDto.unit
        sensor.allocation.clear()

        if (sensorDto.greenhouse != null) {
            val greenhouse = greenhouseRepos.getReferenceById(sensorDto.greenhouse!!.id!!)
            sensor.allocation.add(
                SensorAllocation(
                    greenhouseId = greenhouse.id,
                    sensorId = sensor.id,
                    greenhouse = greenhouse,
                    north = sensorDto.north,
                    west = sensorDto.west,
                    height = sensorDto.height
                )
            )
        }
        repos.save(sensor)
        return sensor.toSensorDTO()
    }

    override fun deleteSensor(sensorId: Long) {
        repos.deleteById(sensorId)
    }

}

