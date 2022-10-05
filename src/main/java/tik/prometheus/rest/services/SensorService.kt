package tik.prometheus.rest.services

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tik.prometheus.rest.dtos.SensorDTO

interface SensorService {
    fun getSensors(pageable: Pageable): Page<SensorDTO>
}