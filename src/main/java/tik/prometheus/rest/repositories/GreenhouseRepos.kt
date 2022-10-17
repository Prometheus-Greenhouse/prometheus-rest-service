package tik.prometheus.rest.repositories

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import tik.prometheus.rest.models.Greenhouse
import java.util.*

interface GreenhouseRepos : JpaRepository<Greenhouse, Long> {
    @Query("SELECT g FROM Greenhouse g WHERE g.farmId = :farmId")
    fun findAllWithParams(farmId: Long, pageable: Pageable): Page<Greenhouse>

}