package tik.prometheus.rest.models

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass

@Entity
@IdClass(SensorRecordId::class)
class SensorRecord(
    @Id
    var greenhouseId: Long? = null,
    @Id
    var sensorId: Long? = null,
    @Id
    var date: LocalDateTime? = null,
    var weather: String? = null,
    var numberOfWeek: String? = null,
    var sensorData: String? = null,
    var lineNumber: String? = null,
)