package tik.prometheus.rest.models

import java.time.LocalDateTime
import java.util.*
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
    @Column(name = """"date"""")
    var date: LocalDateTime? = null,
    var weather: String? = null,
    var numberOfWeek: String? = null,
    var sensorData: String? = null,
    var lineNumber: String? = null,
)