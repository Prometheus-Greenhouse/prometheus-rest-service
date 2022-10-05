package tik.prometheus.rest.dtos

import tik.prometheus.rest.models.Greenhouse

class ActuatorDTO(
    var id: Long? = null,
    var type: String? = null,
    var unit: String? = null,
    var localId: String,
    var greenhouse: GreenhouseDTO? = null,
    var north: Float? = 0f,
    var west: Float? = 0f,
    var height: Float? = 0f,
)