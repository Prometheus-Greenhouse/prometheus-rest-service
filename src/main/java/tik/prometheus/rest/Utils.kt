package tik.prometheus.rest

import tik.prometheus.rest.dtos.SensorDTO
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
