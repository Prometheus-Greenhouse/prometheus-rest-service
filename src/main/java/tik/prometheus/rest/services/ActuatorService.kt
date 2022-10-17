package tik.prometheus.rest.services

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tik.prometheus.rest.dtos.ActuatorDTO
import tik.prometheus.rest.dtos.ActuatorLiteDTO

interface ActuatorService {
    fun getActuators(greenhouseId: Long, pageable: Pageable): Page<ActuatorLiteDTO>

    fun getActuator(id: Long): ActuatorDTO
}