package racoony.software.klubi.event_sourcing

import java.util.UUID

interface EventStore {
    fun save(aggregateId: UUID, events: List<Event>)
    fun loadEvents(aggregateId: UUID): List<Event>
}