package tik.prometheus.rest.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tik.prometheus.rest.models.Sensor;
import tik.prometheus.rest.models.SensorRecord;

import java.util.List;

@Repository
public interface SensorRepos extends JpaRepository<Sensor, Long> {
    @Query("""
            SELECT s
            FROM Sensor s
            LEFT JOIN SensorAllocation sa ON s.id = sa.sensorId
            LEFT JOIN Greenhouse gh ON gh.id = sa.greenhouseId
            WHERE gh.id = :greenhouseId or :greenhouseId is null
            """)
    Page<Sensor> findAllWithParams(@Param("greenhouseId") Long greenhouseId, Pageable pageable);


}
