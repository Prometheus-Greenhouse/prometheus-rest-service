package tik.prometheus.rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tik.prometheus.rest.models.Sensor;

public interface SensorRepos extends JpaRepository<Sensor, Long> {
}
