package tik.prometheus.rest.services.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import tik.prometheus.rest.dtos.GreenhouseSummaryDTO
import tik.prometheus.rest.models.Greenhouse
import tik.prometheus.rest.repositories.GreenhouseRepos
import tik.prometheus.rest.services.GreenhouseService
import tik.prometheus.rest.toGreenhouseSummaryDTO

@Service
class GreenhouseServiceImpl @Autowired constructor(private val greenhouseRepos: GreenhouseRepos) : GreenhouseService {
    override fun getGreenhouses(farmId: Long?, pageable: Pageable): Page<GreenhouseSummaryDTO> {
        val pageEntity = if (farmId != null) greenhouseRepos.findAllWithParams(farmId, pageable) else greenhouseRepos.findAll(pageable)
        return pageEntity.map(Greenhouse::toGreenhouseSummaryDTO)
    }

}