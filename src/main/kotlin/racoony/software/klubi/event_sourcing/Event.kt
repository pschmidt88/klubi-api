package racoony.software.klubi.event_sourcing
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

abstract class Event {
    abstract val version: Long?
    val eventTime: Instant = Clock.System.now()

    abstract fun copyWithVersion(version: Long): Event
}
