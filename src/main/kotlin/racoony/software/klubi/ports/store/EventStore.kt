package racoony.software.klubi.ports.store

import kotlinx.coroutines.flow.Flow
import racoony.software.klubi.event_sourcing.Event
import java.util.UUID

interface EventStore {
    suspend fun save(aggregateId: UUID, events: Iterable<Event>): Result<Unit>
    suspend fun loadEvents(aggregateId: UUID): Flow<Event>
}