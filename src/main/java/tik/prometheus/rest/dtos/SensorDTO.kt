package tik.prometheus.rest.dtos

import tik.prometheus.rest.constants.EUnit
import tik.prometheus.rest.constants.SensorType
import tik.prometheus.rest.models.Sensor
import tik.prometheus.rest.models.SensorAllocation
import tik.prometheus.rest.reflectionToString

class SensorLiteDTO(
    var id: Long? = null,
    var localId: String? = null,
    var address: String? = null,
    var type: SensorType = SensorType.NaN,
    var unit: EUnit = EUnit.NaN,
    var topic: String? = null,
) {
    override fun toString() = reflectionToString(this)

}

class SensorDTO(
    var id: Long? = null,
    var localId: String? = null,
    var address: String? = null,
    var type: SensorType = SensorType.NaN,
    var unit: EUnit = EUnit.NaN,
    var topic: String? = null,
    var greenhouse: GreenhouseLiteDTO? = null,
) {
    override fun toString() = reflectionToString(this)
}

fun Sensor.toSensorLiteDTO() = SensorLiteDTO(
    topic = "sensor/%s".format(id),
    id = id,
    localId = localId,
    address = address,
    type = type ?: SensorType.NaN,
    unit = unit ?: EUnit.NaN,
)

fun Sensor.toSensorDTO() = SensorDTO(
    id = id,
    localId = localId,
    address = address,
    type = type ?: SensorType.NaN,
    unit = unit ?: EUnit.NaN,
    topic = unit?:
)

fun SensorAllocation.toSensorLiteDTO() = SensorLiteDTO(
    topic = "sensor/%s".format(sensor.id),
    id = sensor.id,
    localId = sensor.localId,
    address = sensor.address,
    type = sensor.type ?: SensorType.NaN,
    unit = sensor.unit ?: EUnit.NaN
)