package tik.prometheus.rest.models

import javax.persistence.*

@Entity
@IdClass(ActuatorAllocationId::class)
data class ActuatorAllocation(
    @Id
    var greenhouseId: Long? = null,
    @Id
    var actuatorId: Long? = null,
    var north: Float? = null,
    var west: Float? = null,
    var height: Float? = null,
) {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actuatorId", insertable = false, updatable = false)
    lateinit var actuator: Actuator

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "greenhouseId", insertable = false, updatable = false)
    lateinit var greenhouse: Greenhouse
}