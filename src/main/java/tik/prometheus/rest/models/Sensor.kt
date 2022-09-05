package tik.prometheus.rest.models

import tik.prometheus.rest.ObjectUtils
import tik.prometheus.rest.dtos.SensorDTO
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Sensor(
    @Id
    var id: Long? = null,
    var localId: String? = null,
    var address: String? = null,
    var type: String? = null,
    var unit: String? = null,
) {
    override fun toString(): String {
        return ObjectUtils.reflectionToString(this);
    }

}

fun Sensor.toSensorDTO() = SensorDTO(
    topic = "sensor/%s".format(id),
    id = id,
    localId = localId,
    address = address,
    type = type,
    unit = unit,
)

