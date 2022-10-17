package tik.prometheus.rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import tik.prometheus.rest.models.Sensor;

import java.util.List;

public interface SensorRepos extends JpaRepository<Sensor, Long> {
}
