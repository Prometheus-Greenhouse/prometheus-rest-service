package tik.prometheus.rest.models

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass

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
)
