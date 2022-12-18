package tik.prometheus.rest.dtos

import tik.prometheus.rest.models.Greenhouse
import tik.prometheus.rest.services.GreenhouseService

class GreenhouseLiteDTO(
    var id: Long? = null,
    var farmId: Long? = null,
    var type: String,
    var label: String?,
    var area: Float,
    var height: Float,
    var width: Float,
    var length: Float,
    var cultivationArea: Float,
)

class GreenhouseDTO(
    var id: Long? = null,
    var farmId: Long? = null,
    var type: String,
    var area: Float,
    var height: Float,
    var width: Float,
    var length: Float,
    var cultivationArea: Float,
    var actuators: List<ActuatorLiteDTO> = emptyList(),
    var sensors: List<SensorLiteDTO> = emptyList(),
)

fun Greenhouse.toGreenhouseSummaryDTO(): GreenhouseLiteDTO {
    return GreenhouseLiteDTO(
        id = id,
        farmId = farmId,
        type = type,
        area = area,
        height = height,
        width = width,
        length = length,
        cultivationArea = cultivationArea,
        label = label ?: GreenhouseService.greenhouseLabel(this)
    )
}
