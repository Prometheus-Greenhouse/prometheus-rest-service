package tik.prometheus.rest.dtos

import tik.prometheus.rest.constants.ActuatorType
import tik.prometheus.rest.models.Actuator
import tik.prometheus.rest.models.ActuatorAllocation
import tik.prometheus.rest.reflectionToString

class ActuatorLiteDTO(
    var id: Long? = null,
    var type: ActuatorType = ActuatorType.NaN,
    var unit: String? = null,
    var localId: String,
    var north: Float? = null,
    var west: Float? = null,
    var height: Float? = null,
    var label: String,
) {

    override fun toString() = reflectionToString(this)
}


class ActuatorDTO(
    var id: Long? = null,
    var type: ActuatorType = ActuatorType.NaN,
    var unit: String? = null,
    var localId: String,
    var greenhouse: GreenhouseLiteDTO? = null,
    var north: Float? = null,
    var west: Float? = null,
    var height: Float? = null,
    var label: String,
    var state: ActuatorState = ActuatorState()
) {
    class ActuatorState(
        var isRunning: Boolean = false,
    )

    override fun toString() = reflectionToString(this)
}

fun ActuatorAllocation.toActuatorLiteDTO(): ActuatorLiteDTO {
    return ActuatorLiteDTO(
        id = actuatorId,
        type = actuator.type ?: ActuatorType.NaN,
        unit = actuator.unit,
        localId = actuator.localId ?: "undefined",
        north = north,
        west = west,
        height = height,
        label = "%s".format(actuator.localId)
    )
}

fun Actuator.toActuatorDTO(): ActuatorDTO {
    val greenhouse = allocation?.greenhouse

    val greenhouseDTO = if (greenhouse != null) GreenhouseLiteDTO(
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
        type = type ?: ActuatorType.NaN,
        unit = unit,
        localId = localId ?: "undefined",
        north = allocation?.north,
        west = allocation?.west,
        height = allocation?.height,
        greenhouse = greenhouseDTO,
        label = label ?: "%s".format(localId),
        state = ActuatorDTO.ActuatorState(
            isRunning = isRunning
        )
    )
}

fun Actuator.toActuatorLiteDTO(): ActuatorLiteDTO {
    return ActuatorLiteDTO(
        id = id,
        type = type ?: ActuatorType.NaN,
        unit = unit,
        localId = localId ?: "undefined",
        north = allocation?.north,
        west = allocation?.west,
        height = allocation?.height,
        label = label ?: "%s".format(localId)
    )
}
