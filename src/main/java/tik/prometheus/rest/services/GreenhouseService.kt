package tik.prometheus.rest.services

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tik.prometheus.rest.dtos.GreenhouseDTO
import tik.prometheus.rest.dtos.GreenhouseLiteDTO

interface GreenhouseService {
    fun getGreenhouses(farmId: Long?, pageable: Pageable): Page<GreenhouseLiteDTO>

    fun getGreenhouse(id: Long): GreenhouseDTO
}