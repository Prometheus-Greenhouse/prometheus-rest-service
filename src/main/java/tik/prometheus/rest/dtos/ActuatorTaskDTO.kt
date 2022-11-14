package tik.prometheus.rest.dtos

import tik.prometheus.rest.models.ActuatorTask

class ActuatorTaskDTO(
    var sensorId: Long,
    var startValue: Float,
    var limitValue: Float,
)

fun ActuatorTask.toDTO(): ActuatorTaskDTO{
    return ActuatorTaskDTO(
        sensorId=sensorId!!,
        startValue=startValue!!,
        limitValue=limitValue!!
    )
}