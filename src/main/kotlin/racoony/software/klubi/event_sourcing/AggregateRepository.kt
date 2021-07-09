package racoony.software.klubi.event_sourcing

import racoony.software.klubi.ports.bus.EventBus
import racoony.software.klubi.ports.store.EventStore
import java.util.UUID
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

@ApplicationScoped
class AggregateRepository<T : Aggregate>(
    @Inject private val eventStore: EventStore,
    @Inject private val eventBus: EventBus
) {
    fun <T: Aggregate> findById(id: UUID, aggregate: () -> T): T {
        val history = this.eventStore.loadEvents(id)

        return aggregate().apply {
            fromHistory(history)
        }
    }

    fun save(aggregate: T) {
        this.eventStore.save(aggregate.id, aggregate.changes)
        aggregate.changes.forEach {
            this.eventBus.publish(it)
        }
    }
}