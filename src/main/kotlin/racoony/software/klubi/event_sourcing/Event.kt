package racoony.software.klubi.event_sourcing

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonTypeInfo
import java.time.Instant

//@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
//@JsonIgnoreProperties(ignoreUnknown = true)
abstract class Event(
    private val version: Long?,
    val eventTime: Instant = Instant.now()
) {
    fun version(): Long? = version
    abstract fun copyWithVersion(version: Long): Event
}
