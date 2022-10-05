package tik.prometheus.rest.models

import tik.prometheus.rest.constants.GreenhouseType
import javax.persistence.*

@Entity
@Table(name = "greenhouse")
class Greenhouse(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var farmId: Long? = null,
    var type: String = GreenhouseType.NORMAL.value,
    var area: Float = 0f,
    var height: Float = 0f,
    var width: Float = 0f,
    var length: Float = 0f,
    var cultivationArea: Float = 0f,
) {
}