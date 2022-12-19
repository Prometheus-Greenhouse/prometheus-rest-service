package tik.prometheus.rest.dtos

import tik.prometheus.rest.constants.ActuatorTaskType
import tik.prometheus.rest.models.ActuatorTask

class ActuatorTaskDTO(
    var sensorId: Long,
    var taskType: ActuatorTaskType,
    var startValue: Float,
    var limitValue: Float,
)

fun ActuatorTask.toDTO(): ActuatorTaskDTO {
    return ActuatorTaskDTO(
        sensorId = sensorId!!,
        taskType = taskType!!,
        startValue = startValue!!,
        limitValue = limitValue!!
    )
}