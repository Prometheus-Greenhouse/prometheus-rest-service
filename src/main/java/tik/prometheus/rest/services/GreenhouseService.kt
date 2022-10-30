package tik.prometheus.rest.services

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tik.prometheus.rest.dtos.GreenhouseDTO
import tik.prometheus.rest.dtos.GreenhouseLiteDTO
import tik.prometheus.rest.models.Greenhouse

interface GreenhouseService {
    fun getGreenhouses(farmId: Long?, pageable: Pageable): Page<GreenhouseLiteDTO>

    fun getGreenhouse(id: Long): GreenhouseDTO
    companion object {
        fun greenhouseLabel(greenhouse: Greenhouse): String {
            return greenhouse.label ?: "Greenhouse %s".format(greenhouse.id)
        }
    }
}