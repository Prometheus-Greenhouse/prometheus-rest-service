package tik.prometheus.rest

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class Configurations(
    @Value("\${broker.host}")
    var brokerHost: String? = null,
    @Value("\${broker.port}")
    var brokerPort: String? = null,
    var debug: Boolean? = null,
)