package tik.prometheus.rest.dtos

open class GreenhouseSummaryDTO(
    open var id: Long? = null,
    open var farmId: Long,
    open var type: String,
    open var area: Float,
    open var height: Float,
    open var width: Float,
    open var length: Float,
    open var cultivationArea: Float,
)

class GreenhouseDTO(
    override var id: Long? = null,
    override var farmId: Long,
    override var type: String,
    override var area: Float,
    override var height: Float,
    override var width: Float,
    override var length: Float,
    override var cultivationArea: Float,
    var actuators: List<ActuatorDTO> = emptyList(),
    var sensors: List<SensorDTO> = emptyList(),
) : GreenhouseSummaryDTO(
    id, farmId, type, area, height, width, length, cultivationArea
)
