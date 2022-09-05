package tik.prometheus.rest.dtos

import tik.prometheus.rest.ObjectUtils
import tik.prometheus.rest.models.Sensor
import tik.prometheus.rest.models.toSensorDTO

class SensorDTO(
    var id: Long? = null,
    var localId: String? = null,
    var address: String? = null,
    var type: String? = null,
    var unit: String? = null,
    var topic: String? = null,
) {

    override fun toString(): String {
        return ObjectUtils.reflectionToString(this);
    }

}


