package tik.prometheus.rest.models

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass

@Entity
@IdClass(ActuatorAllocationId::class)
class ActuatorAllocation(
    @Id
    var greenhouseId: Long? = null,
    @Id
    var actuatorId: Long? = null,
    var north: Float? = null,
    var west: Float? = null,
    var height: Float? = null,
) {

}