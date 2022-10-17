package tik.prometheus.rest.services.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import tik.prometheus.rest.dtos.ActuatorDTO
import tik.prometheus.rest.dtos.ActuatorLiteDTO
import tik.prometheus.rest.dtos.toActuatorDTO
import tik.prometheus.rest.dtos.toActuatorLiteDTO
import tik.prometheus.rest.models.Actuator
import tik.prometheus.rest.repositories.ActuatorRepos
import tik.prometheus.rest.services.ActuatorService

@Service
class ActuatorServiceImpl @Autowired constructor(
    private val actuatorRepos: ActuatorRepos
) : ActuatorService {

    override fun getActuators(greenhouseId: Long, pageable: Pageable): Page<ActuatorLiteDTO> {
        val pageEntity = actuatorRepos.findAllWithParams(greenhouseId, pageable)
        return pageEntity.map(Actuator::toActuatorLiteDTO)
    }

    override fun getActuator(id: Long): ActuatorDTO {
        return actuatorRepos.findById(id).map(Actuator::toActuatorDTO).orElseThrow { throw ResponseStatusException(HttpStatus.NOT_FOUND, "No result found") }
    }
}