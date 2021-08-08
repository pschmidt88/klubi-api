package racoony.software.klubi.event_sourcing

import io.smallrye.mutiny.Uni
import org.jboss.logging.Logger
import racoony.software.klubi.ports.bus.EventBus
import racoony.software.klubi.ports.store.EventStore
import java.util.UUID
import java.util.function.Consumer
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class AggregateRepository<T : Aggregate>(
    @Inject var eventStore: EventStore,
    @Inject var eventBus: EventBus
) {
    fun <T : Aggregate> findById(id: UUID, aggregate: () -> T): Uni<T> {
        return this.eventStore.loadEvents(id)
            .collect().asList()
            .onItem().transform { events ->
                aggregate().apply { fromHistory(events) }
            }
    }

    fun save(aggregate: T): Uni<Void> {
        return this.eventStore.save(aggregate.id, aggregate.changes)
            .onItem().invoke(Consumer {
                aggregate.changes.forEach {
                    this.eventBus.publish(it)
                }
            })
    }
}
