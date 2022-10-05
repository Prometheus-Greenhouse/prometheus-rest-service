package tik.prometheus.rest

import tik.prometheus.rest.dtos.ActuatorDTO
import tik.prometheus.rest.dtos.GreenhouseDTO
import tik.prometheus.rest.dtos.GreenhouseSummaryDTO
import tik.prometheus.rest.dtos.SensorDTO
import tik.prometheus.rest.models.Actuator
import tik.prometheus.rest.models.Greenhouse
import tik.prometheus.rest.models.Sensor
import java.lang.reflect.Modifier
import java.util.*

fun reflectionToString(obj: Any): String {
    val s = LinkedList<String>()
    var clazz: Class<in Any>? = obj.javaClass
    while (clazz != null) {
        for (prop in clazz.declaredFields.filterNot { Modifier.isStatic(it.modifiers) }) {
            prop.isAccessible = true
            s += "${prop.name}=" + prop.get(obj)?.toString()?.trim()
        }
        clazz = clazz.superclass
    }
    return "${obj.javaClass.simpleName}=[${s.joinToString(", ")}]"
}

fun Sensor.toSensorDTO() = SensorDTO(
    topic = "sensor/%s".format(id),
    id = id,
    localId = localId,
    address = address,
    type = type,
    unit = unit,
)


fun Actuator.toActuatorDTO(): ActuatorDTO {
    val greenhouse = allocation?.greenhouse

    val greenhouseDTO = if (greenhouse != null) GreenhouseDTO(
        id = greenhouse.id,
        farmId = greenhouse.farmId!!,
        type = greenhouse.type,
        area = greenhouse.area,
        height = greenhouse.height,
        width = greenhouse.width,
        length = greenhouse.length,
        cultivationArea = greenhouse.cultivationArea,
    ) else null;

    return ActuatorDTO(
        id = id,
        type = type,
        unit = unit,
        localId = localId ?: "undefined",
        greenhouse = greenhouseDTO,
        north = allocation?.north,
        west = allocation?.west,
        height = allocation?.height
    )
}

fun Greenhouse.toGreenhouseSummaryDTO(): GreenhouseSummaryDTO {
    return GreenhouseSummaryDTO(
        id = id,
        farmId = farmId!!,
        type = type,
        area = area,
        height = height,
        width = width,
        length = length,
        cultivationArea = cultivationArea,
    )
}
