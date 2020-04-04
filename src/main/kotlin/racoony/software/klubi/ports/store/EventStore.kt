package racoony.software.klubi.ports.store

import java.util.UUID
import racoony.software.klubi.event_sourcing.Event

interface EventStore {
    fun save(aggregateId: UUID, events: List<Event>)
    fun loadEvents(aggregateId: UUID): List<Event>
}
