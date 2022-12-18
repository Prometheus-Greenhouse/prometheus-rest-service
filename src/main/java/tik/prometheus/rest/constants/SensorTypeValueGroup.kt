package tik.prometheus.rest.constants

class V(val from: Float, val to: Float)
class SensorTypeValueGroup {

}

class HumidityValue(
    val HIGH: V = V(20F, Float.POSITIVE_INFINITY),
    val MID: V = V(10F, 20F),
    val LOW: V = V(Float.NEGATIVE_INFINITY, 0F)
) {
}

class TemperatureValue(
    val HIGH: V,
    val MID: V,
    val LOW: V,
)
