package racoony.software.klubi.ports.store

import racoony.software.klubi.event_sourcing.Event
import java.util.UUID

interface EventStore {
    fun save(aggregateId: UUID, events: List<Event>)
    fun loadEvents(aggregateId: UUID): List<Event>
}