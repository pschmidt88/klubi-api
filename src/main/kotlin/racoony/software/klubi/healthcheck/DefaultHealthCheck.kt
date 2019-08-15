package racoony.software.klubi.healthcheck

import com.codahale.metrics.health.HealthCheck

class DefaultHealthCheck : HealthCheck() {
    override fun check(): Result {
        return Result.healthy()
    }
}
