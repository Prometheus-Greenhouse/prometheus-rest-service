package tik.prometheus.rest.models

import com.google.gson.Gson
import tik.prometheus.rest.constants.SensorType
import java.sql.Clob
import javax.persistence.*

@Entity
@Table(name = "SENSOR_TYPE_METADATA")
class SensorTypeMetadata(
    @Id
    @Enumerated(EnumType.STRING)
    var type: SensorType? = null,
    @Lob
    var content: Clob? = null
) {

    fun <T> getContent(type: Class<T>): T? {
        if (content == null) {
            return null;
        }
        return Gson().fromJson(content!!.characterStream.readText(), type);
    }
}