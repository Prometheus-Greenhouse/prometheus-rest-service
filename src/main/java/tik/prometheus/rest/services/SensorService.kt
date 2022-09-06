package tik.prometheus.rest.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import tik.prometheus.rest.dtos.SensorDTO
import tik.prometheus.rest.repositories.SensorRepos
import tik.prometheus.rest.toSensorDTO

@Service
class SensorService @Autowired constructor(
    private val sensorRepos: SensorRepos,
) {
    fun getSensors(pageable: Pageable): Page<SensorDTO> {
        val pageEntity = sensorRepos.findAll(pageable)
        return pageEntity.map { entity -> entity.toSensorDTO() }
    }
}

