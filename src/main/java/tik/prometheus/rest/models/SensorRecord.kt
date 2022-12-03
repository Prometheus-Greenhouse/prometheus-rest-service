package tik.prometheus.rest.models

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "SENSOR_RECORD")
class SensorRecord(
    @Id
    var id: Long? = null,
    var greenhouseId: Long? = null,
    var sensorId: Long? = null,
    var createdAt: LocalDateTime? = null,
    var weather: String? = null,
    var numberOfWeek: String? = null,
    var sensorData: String? = null,
    var lineNumber: String? = null,
)