package racoony.software.klubi.event_sourcing

import jakarta.enterprise.context.Dependent
import kotlinx.coroutines.flow.toList
import racoony.software.klubi.ports.bus.EventBus
import racoony.software.klubi.ports.bus.publishAsync
import racoony.software.klubi.ports.store.EventStore
import java.util.UUID

@Dependent
class AggregateRepository<T : AggregateRoot>(
    private val eventStore: EventStore,
    private val eventBus: EventBus,
) {
    suspend fun <T : AggregateRoot> findById(id: UUID, aggregate: () -> T): T =
        this.eventStore.loadEvents(id).toList().let { events ->
            aggregate().apply { fromHistory(events) }
        }

    suspend fun save(aggregate: T): Result<Unit> = this.eventStore.save(aggregate.id, aggregate.uncommittedChanges).also {
        aggregate.uncommittedChanges.forEach(eventBus::publishAsync)
    }
}
