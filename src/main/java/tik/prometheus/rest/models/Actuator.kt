package tik.prometheus.rest.models

import javax.persistence.*

@Entity
class Actuator(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var type: String? = null,
    var unit: String? = null,
    var localId: String? = null,
    @OneToOne(mappedBy = "actuator")
    var allocation: ActuatorAllocation? = null,
)