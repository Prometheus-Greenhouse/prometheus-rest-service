package tik.prometheus.rest.services.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import tik.prometheus.rest.dtos.SensorDTO
import tik.prometheus.rest.models.Sensor
import tik.prometheus.rest.repositories.SensorRepos
import tik.prometheus.rest.services.SensorService
import tik.prometheus.rest.toSensorDTO

@Service
class SensorServiceImpl @Autowired constructor(
    private val sensorRepos: SensorRepos,
) : SensorService {
    override fun getSensors(pageable: Pageable): Page<SensorDTO> {
        val pageEntity = sensorRepos.findAll(pageable)
        return pageEntity.map(Sensor::toSensorDTO)
    }
}

