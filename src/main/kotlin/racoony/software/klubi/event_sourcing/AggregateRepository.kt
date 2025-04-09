package racoony.software.klubi.event_sourcing

import arrow.core.None
import arrow.core.Option
import arrow.core.flatMap
import arrow.core.right
import jakarta.enterprise.context.Dependent
import kotlinx.coroutines.flow.toList
import racoony.software.klubi.domain.Failure
import racoony.software.klubi.domain.Result
import racoony.software.klubi.domain.Success
import racoony.software.klubi.ports.bus.EventBus
import racoony.software.klubi.ports.bus.publishAsync
import java.util.UUID

@Dependent
class AggregateRepository<T : AggregateRoot>(
    private val eventStore: EventStore,
) {
    suspend fun <T : AggregateRoot> findById(id: UUID, aggregate: () -> T): T =
        this.eventStore.findEventsByAggregateId(id)
            .map { events -> aggregate().apply { fromHistory(events) } }
            .getOrNull() ?: TODO("error handling")

    suspend fun save(aggregate: T, expectedVersion: Option<Long> = None): Result<Failure, Success> {
        return eventStore.saveEvents(aggregate.id, aggregate.uncommittedChanges, expectedVersion)
            .flatMap {
                aggregate.markChangesAsCommited()
                ChangesSavedSuccessfully.right()
            }
    }
}

object ChangesSavedSuccessfully : Success
