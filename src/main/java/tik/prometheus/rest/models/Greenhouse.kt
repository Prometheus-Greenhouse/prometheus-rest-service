package tik.prometheus.rest.models

import javax.persistence.*

@Entity
@Table(name = "greenhouse")
class Greenhouse(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var farmId: Long? = null,
    var type: String? = null,
    var area: Float? = null,
    var height: Float? = null,
    var width: Float? = null,
    var length: Float? = null,
    var cultivationArea: Float? = null,
) {
}