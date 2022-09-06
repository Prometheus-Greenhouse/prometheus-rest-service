package tik.prometheus.rest.dtos

import tik.prometheus.rest.reflectionToString

class SensorDTO(
    var id: Long? = null,
    var localId: String? = null,
    var address: String? = null,
    var type: String? = null,
    var unit: String? = null,
    var topic: String? = null,
) {

    override fun toString(): String {
        return reflectionToString(this);
    }

}


