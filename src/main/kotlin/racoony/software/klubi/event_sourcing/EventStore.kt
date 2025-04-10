package racoony.software.klubi.event_sourcing

import arrow.core.Option
import racoony.software.klubi.domain.Failure
import racoony.software.klubi.domain.Result
import java.util.UUID
import kotlin.uuid.Uuid

interface EventStore {
    suspend fun saveEvents(
        aggregateId: AggregateId,
        events: Iterable<Event>,
        expectedVersion: Option<Long>
    ): Result<EventStoreFailure, Iterable<Event>>

    suspend fun findEventsByAggregateId(aggregateId: AggregateId): Option<Iterable<Event>>
}

sealed class EventStoreFailure : Failure {
    data object VersionMismatch : EventStoreFailure()
}
