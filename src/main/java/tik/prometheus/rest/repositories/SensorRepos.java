package tik.prometheus.rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tik.prometheus.rest.models.Sensor;

@Repository
public interface SensorRepos extends JpaRepository<Sensor, Long> {
//    @Query("DELETE FROM SensorAllocation sa WHERE sa.sensorId=:sensorId")
//    public void removeAllocation(Long sensorId);
}
