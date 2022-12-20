package tik.prometheus.rest.constants

import tik.prometheus.rest.dtos.RangeDTO

enum class SensorValueGroup {
    HIGH,
    MID,
    LOW;
    companion object{
        const val NEGATIVE_INFINITY = -99999F;
        const val POSITIVE_INFINITY = 99999F;
    }
}


class HumidityValue(
    val HIGH: RangeDTO = RangeDTO(20F, Float.POSITIVE_INFINITY),
    val MID: RangeDTO = RangeDTO(10F, 20F),
    val LOW: RangeDTO = RangeDTO(Float.NEGATIVE_INFINITY, 0F)
) {
}

class TemperatureValue(
    val HIGH: RangeDTO,
    val MID: RangeDTO,
    val LOW: RangeDTO,
)
