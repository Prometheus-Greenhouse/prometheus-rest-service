package tik.prometheus.rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tik.prometheus.rest.models.SensorRecord;
import tik.prometheus.rest.models.SensorRecordId;

public interface SensorRecordRepos extends JpaRepository<SensorRecord, SensorRecordId> {
}
