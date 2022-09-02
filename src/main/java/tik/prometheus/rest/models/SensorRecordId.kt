package tik.prometheus.rest.models

import java.io.Serializable
import java.time.LocalDateTime

class SensorRecordId(
    var greenhouseId: Long? = null,
    var sensorId: Long? = null,
    var date: LocalDateTime? = null,
) : Serializable