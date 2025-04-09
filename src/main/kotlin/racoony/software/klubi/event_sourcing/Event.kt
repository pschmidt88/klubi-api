package racoony.software.klubi.event_sourcing


import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
abstract class Event(
    private val version: Long?,
    val eventTime: Instant = Clock.System.now()
) {
    fun version(): Long? = version
    abstract fun copyWithVersion(version: Long): Event
}
