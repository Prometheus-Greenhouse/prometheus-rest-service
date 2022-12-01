package tik.prometheus.rest

import tik.prometheus.rest.models.SensorRecord

class DecisionTree {
    companion object {
        fun calcEntropy(p: Double, i: Array<Double>): Double {
            i.forEach { println(it) }
            return i.sumOf { -it / p * (Math.log(it / p) / Math.log(2.0)) }
        }

        fun <T> simplize(sensorRecords: MutableList<SensorRecord>, get: (Double) -> T): List<SimplziedRecord<T>> {
            sensorRecords.removeIf {
                try {
                    it.sensorData!!.toFloat()
                    false
                } catch (e: NumberFormatException) {
                    true
                } catch (e: NullPointerException) {
                    true
                }
            }
            return sensorRecords.map {
                val value = it.sensorData!!.toDouble()
                SimplziedRecord(
                    value = value,
                    group = get(value)
                )
            }
        }

    }
}


enum class TemperatureGroup(val start: Double, val end: Double) {
    HOT(34.0, Double.MAX_VALUE),
    MID(20.0, 33.0),
    COOL(Double.MIN_VALUE, 19.0);

    companion object {
        fun getGroup(value: Double): TemperatureGroup {
            TemperatureGroup.values().forEach {
                if (it.start <= value && value <= it.end) {
                    return it;
                }
            }
            throw IllegalCallerException("value invalid")
        }
    }
}

enum class DayCycle(val start: Double, val end: Double) {
    DAY(6.0, 18.0),
    NIGHT(17.0, 5.0);

    companion object {
        fun getGroup(value: Double): DayCycle {
            DayCycle.values().forEach {
                if (it.start <= value && value <= it.end) {
                    return it
                }
                if (it.start >= it.end
                    && ((it.start <= value && value < 24) || (0 <= value && value <= it.end))
                ) {
                    return it
                }
            }
            throw IllegalCallerException("value invalid %s".format(value))
        }
    }
}

class SimplziedRecord<T>(
    var value: Double,
    var group: T,
)

fun main(args: Array<String>) {

    val temperatureData =
        arrayOf(35, 35, 35, 21, 18, 18, 18, 21, 18, 21, 21, 21, 38, 21)
            .map { SensorRecord(sensorData = it.toString()) }
            .toMutableList()
    val dateData =
        arrayOf(7, 7, 20, 20, 20, 20, 7, 7, 7, 21, 8, 21, 21, 6)
            .map { SensorRecord(sensorData = it.toString()) }
            .toMutableList()

    val rs = DecisionTree.simplize(dateData, DayCycle::getGroup)

    val grouped = rs.groupBy { it.group }
    println(grouped)
    println(DecisionTree.calcEntropy(14.0, grouped.values.map { it.size.toDouble() }.toTypedArray()))
}