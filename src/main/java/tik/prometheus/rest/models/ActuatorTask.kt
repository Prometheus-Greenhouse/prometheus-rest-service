package tik.prometheus.rest.models

import java.io.Serializable
import javax.persistence.*


@Entity
@IdClass(ActuatorTask.ActuatorTaskId::class)
class ActuatorTask(
    @Id
    var actuatorId: Long? = null,
    @Id
    var sensorId: Long? = null,
    var startValue: Float? = null,
    var limitValue: Float? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actuatorId", insertable = false, updatable = false)
    var actuator: Actuator? = null,
    @ManyToOne
    @JoinColumn(name = "sensorId", insertable = false, updatable = false)
    var sensor: Sensor? = null
) {
    class ActuatorTaskId(
        @Id
        var actuatorId: Long? = null,
        @Id
        var sensorId: Long? = null,
    ) : Serializable
}