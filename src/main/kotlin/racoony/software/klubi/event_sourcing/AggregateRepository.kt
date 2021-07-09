package racoony.software.klubi.event_sourcing

import io.smallrye.mutiny.Uni
import racoony.software.klubi.ports.bus.EventBus
import racoony.software.klubi.ports.store.EventStore
import java.util.UUID
import java.util.function.Consumer
import java.util.function.Function
import java.util.function.Supplier
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

@ApplicationScoped
class AggregateRepository<T : Aggregate>(
    @Inject private val eventStore: EventStore,
    @Inject private val eventBus: EventBus
) {
    fun <T: Aggregate> findById(id: UUID, aggregate: () -> T): Uni<T> {
        return this.eventStore.loadEvents(id)
            .collect().asList()
            .onItem()
            .transform { events -> aggregate().apply { fromHistory(events) } }
    }

    fun save(aggregate: T) {
        this.eventStore.save(aggregate.id, aggregate.changes)
        aggregate.changes.forEach {
            this.eventBus.publish(it)
        }
    }
}