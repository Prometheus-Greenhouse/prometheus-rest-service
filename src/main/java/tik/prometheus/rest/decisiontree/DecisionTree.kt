package tik.prometheus.rest.decisiontree

import tik.prometheus.rest.constants.DayCycle
import tik.prometheus.rest.models.Sensor
import tik.prometheus.rest.models.SensorRecord

class DecisionTree {
    companion object {
        fun calcEntropy(p: Double, i: Array<Double>): Double {
            i.forEach { println(it) }
            return i.sumOf { -it / p * (Math.log(it / p) / Math.log(2.0)) }
        }

        fun <T> simplize(sensorRecords: MutableList<SensorRecord>, get: (Double) -> T): List<SimplizedRecord<T>> {
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
                SimplizedRecord(
                    value = value,
                    group = get(value)
                )
            }
        }

    }
}


class SimplizedRecord<T>(
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

    val rs = DecisionTree.simplize(dateData, DayCycle.Companion::getGroup)

    val grouped = rs.groupBy { it.group }
    println(grouped)
    println(DecisionTree.calcEntropy(14.0, grouped.values.map { it.size.toDouble() }.toTypedArray()))
}