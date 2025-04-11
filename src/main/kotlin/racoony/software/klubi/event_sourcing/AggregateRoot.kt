package racoony.software.klubi.event_sourcing

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

typealias AggregateId = UUID

abstract class AggregateRoot(
    val id: AggregateId = UUID.randomUUID()
) {
    private var version = 0

    companion object {
        private val log: Logger = LoggerFactory.getLogger(AggregateRoot::class.java)
    }
    private val _uncommittedChanges: MutableList<Event> = mutableListOf()

    val uncommittedChanges: Iterable<Event> get() = _uncommittedChanges.toList().asIterable()

    protected fun raise(event: Event) {
        log.debug("Applying {}", event)
        applyEventAndIncrementVersion(event)

        log.debug("Queuing uncommitted changes")
        _uncommittedChanges.add(event)
    }

    private fun applyEventAndIncrementVersion(event: Event) {
        applyEvent(event)
        version = version.inc()
    }

    protected abstract fun applyEvent(event: Event): AggregateRoot

    fun fromHistory(history: Iterable<Event>) {
        history.fold(this) { agg, event -> agg.applyEvent(event) }
    }

    fun markChangesAsCommited() {
        log.debug("Marking changes as commited")
        _uncommittedChanges.clear()
    }
}
