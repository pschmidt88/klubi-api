package racoony.software.klubi.event_sourcing

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.uuid.Uuid

typealias AggregateId = Uuid

abstract class AggregateRoot(
    val id: AggregateId = Uuid.random()
) {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(AggregateRoot::class.java)
    }
    private val _uncommittedChanges: MutableList<Event> = mutableListOf()

    val uncommittedChanges: Iterable<Event> get() = _uncommittedChanges.toList().asIterable()

    protected fun raise(event: Event) {
        log.debug("Applying {}", event)
        applyEvent(event)

        log.debug("Queuing uncommitted changes")
        _uncommittedChanges.add(event)
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
