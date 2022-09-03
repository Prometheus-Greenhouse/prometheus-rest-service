package tik.prometheus.rest.models

import tik.prometheus.rest.ObjectUtils
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
){
    override fun toString():String{
        return ObjectUtils.reflectionToString(this);
    }
}