package tik.prometheus.rest.services.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import tik.prometheus.rest.dtos.GreenhouseDTO
import tik.prometheus.rest.dtos.GreenhouseSummaryDTO
import tik.prometheus.rest.models.Actuator
import tik.prometheus.rest.models.Greenhouse
import tik.prometheus.rest.models.Sensor
import tik.prometheus.rest.repositories.ActuatorRepos
import tik.prometheus.rest.repositories.GreenhouseRepos
import tik.prometheus.rest.repositories.SensorRepos
import tik.prometheus.rest.services.GreenhouseService
import tik.prometheus.rest.toActuatorDTO
import tik.prometheus.rest.toGreenhouseSummaryDTO
import tik.prometheus.rest.toSensorDTO

@Service
class GreenhouseServiceImpl @Autowired constructor(
    private val greenhouseRepos: GreenhouseRepos,
    private val sensorRepos: SensorRepos,
    private val actuatorRepos: ActuatorRepos
) : GreenhouseService {
    override fun getGreenhouses(farmId: Long?, pageable: Pageable): Page<GreenhouseSummaryDTO> {
        val pageEntity = if (farmId != null) greenhouseRepos.findAllWithParams(farmId, pageable) else greenhouseRepos.findAll(pageable)
        return pageEntity.map(Greenhouse::toGreenhouseSummaryDTO)
    }

    override fun getGreenhouse(id: Long): GreenhouseDTO {
        return greenhouseRepos.findById(id).map {
            GreenhouseDTO(
                it.id,
                it.farmId,
                it.type,
                it.area,
                it.height,
                it.width,
                it.length,
                it.cultivationArea,
                actuatorRepos.findAll().map(Actuator::toActuatorDTO),
                sensorRepos.findAll().map(Sensor::toSensorDTO)
            )
        }.orElseThrow { throw ResponseStatusException(HttpStatus.NOT_FOUND, "Not result found") }
    }

}