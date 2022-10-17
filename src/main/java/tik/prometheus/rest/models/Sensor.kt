package tik.prometheus.rest.models

import tik.prometheus.rest.constants.SensorType
import tik.prometheus.rest.constants.EUnit
import tik.prometheus.rest.reflectionToString
import javax.persistence.*

@Entity
class Sensor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var localId: String? = null,
    var address: String? = null,
    @Enumerated(EnumType.STRING)
    var type: SensorType? = SensorType.NaN,
    @Enumerated(EnumType.STRING)
    var unit: EUnit? = EUnit.NaN,
    var label: String? = null
) {
    override fun toString(): String {
        return reflectionToString(this);
    }

}
