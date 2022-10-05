package tik.prometheus.rest.services.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import tik.prometheus.rest.dtos.ActuatorDTO
import tik.prometheus.rest.models.Actuator
import tik.prometheus.rest.repositories.ActuatorRepos
import tik.prometheus.rest.services.ActuatorService
import tik.prometheus.rest.toActuatorDTO

@Service
class ActuatorServiceImpl @Autowired constructor(
    private val actuatorRepos: ActuatorRepos
) : ActuatorService {

    override fun getActuators(greenhouseId: Long, pageable: Pageable): Page<ActuatorDTO> {
        val pageEntity = actuatorRepos.findAllWithParams(greenhouseId, pageable)
        return pageEntity.map(Actuator::toActuatorDTO)
    }
}