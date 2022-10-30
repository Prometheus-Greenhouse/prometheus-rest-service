package tik.prometheus.rest.models

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@IdClass(SensorRecordId::class)
@Table(name = "SENSOR_RECORD")
class SensorRecord(
    @Id
    var greenhouseId: Long? = null,
    @Id
    var sensorId: Long? = null,
    @Id
    @Column(name=""""date"""")
    var date: LocalDateTime? = null,
    var weather: String? = null,
    var numberOfWeek: String? = null,
    var sensorData: String? = null,
    var lineNumber: String? = null,
)