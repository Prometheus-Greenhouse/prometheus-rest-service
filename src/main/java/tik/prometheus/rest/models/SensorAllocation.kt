package tik.prometheus.rest.models

import javax.persistence.*

@Entity
@IdClass(SensorAllocationId::class)
class SensorAllocation(
    @Id
    var greenhouseId: Long? = null,
    @Id
    var sensorId: Long? = null,

    var north: Float? = null,
    var west: Float? = null,
    var height: Float? = null,
) {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensorId", insertable = false, updatable = false)
    lateinit var sensor: Sensor

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "greenhouseId", insertable = false, updatable = false)
    lateinit var greenhouse: Greenhouse
}
