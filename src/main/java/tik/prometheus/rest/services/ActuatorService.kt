package tik.prometheus.rest.services

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tik.prometheus.rest.dtos.ActuatorDTO

interface ActuatorService {
    fun getActuators(greenhouseId: Long, pageable: Pageable): Page<ActuatorDTO>
}