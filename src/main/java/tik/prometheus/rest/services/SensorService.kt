package tik.prometheus.rest.services

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tik.prometheus.rest.dtos.SensorDTO
import tik.prometheus.rest.dtos.SensorLiteDTO
import tik.prometheus.rest.models.Sensor

interface SensorService {
    fun getSensors(pageable: Pageable): Page<SensorLiteDTO>
    fun getSensor(id: Long): SensorDTO
    fun updateSensor(sensorId: Long, sensorDto: SensorDTO): SensorDTO

    fun deleteSensor(sensorId: Long)

    companion object {
        fun sensorTopic(sensor: Sensor): String {
            return "sensor/%s".format(sensor.id)
        }
    }
}