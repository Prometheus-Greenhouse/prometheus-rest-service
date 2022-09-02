package tik.prometheus.rest.models

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Sensor(
    @Id
    var id: Long? = null,
    var localId: String? = null,
    var address: String? = null,
    var type: String? = null,
    var unit: String? = null,
)