package tik.prometheus.rest.services

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tik.prometheus.rest.dtos.GreenhouseSummaryDTO

interface GreenhouseService {
    fun getGreenhouses(farmId: Long?, pageable: Pageable): Page<GreenhouseSummaryDTO>
}