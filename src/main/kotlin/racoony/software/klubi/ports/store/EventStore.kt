package racoony.software.klubi.ports.store

import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import racoony.software.klubi.event_sourcing.Event
import java.util.UUID

interface EventStore {
    fun save(aggregateId: UUID, events: List<Event>): Uni<Void>
    fun loadEvents(aggregateId: UUID): Multi<Event>
}