package tik.prometheus.rest.repositories

import org.springframework.data.jpa.repository.JpaRepository
import tik.prometheus.rest.models.Greenhouse

interface GreenhouseRepos : JpaRepository<Greenhouse, Long> {
}