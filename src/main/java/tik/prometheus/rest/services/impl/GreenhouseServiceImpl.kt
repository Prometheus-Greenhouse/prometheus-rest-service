package tik.prometheus.rest.services.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import tik.prometheus.rest.dtos.*
import tik.prometheus.rest.models.ActuatorAllocation
import tik.prometheus.rest.models.Greenhouse
import tik.prometheus.rest.models.SensorAllocation
import tik.prometheus.rest.repositories.ActuatorRepos
import tik.prometheus.rest.repositories.GreenhouseRepos
import tik.prometheus.rest.repositories.SensorRepos
import tik.prometheus.rest.services.GreenhouseService

@Service
class GreenhouseServiceImpl @Autowired constructor(
    private val greenhouseRepos: GreenhouseRepos,
    private val sensorRepos: SensorRepos,
    private val actuatorRepos: ActuatorRepos
) : GreenhouseService {
    override fun getGreenhouses(farmId: Long?, pageable: Pageable): Page<GreenhouseLiteDTO> {
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
                it.actuatorAllocations.map(ActuatorAllocation::toActuatorLiteDTO),
                it.sensorAllocations.map(SensorAllocation::toSensorLiteDTO)
            )
        }.orElseThrow { throw ResponseStatusException(HttpStatus.NOT_FOUND, "No result found") }
    }

}