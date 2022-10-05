package tik.prometheus.rest.repositories

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import tik.prometheus.rest.models.Actuator

interface ActuatorRepos : JpaRepository<Actuator, Long> {
    @Query("SELECT a FROM Actuator a WHERE a.allocation.greenhouseId = :greenhouseId")
    fun findAllWithParams(@Param("greenhouseId") greenhouseId: Long, pageable: Pageable): Page<Actuator>
}