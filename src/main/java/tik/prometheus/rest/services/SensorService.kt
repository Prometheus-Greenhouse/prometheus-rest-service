package tik.prometheus.rest.services

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tik.prometheus.rest.dtos.SensorDTO
import tik.prometheus.rest.dtos.SensorLiteDTO

interface SensorService {
    fun getSensors(pageable: Pageable): Page<SensorLiteDTO>
    fun getSensor(id: Long): SensorDTO
}