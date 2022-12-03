package tik.prometheus.rest.constants

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
