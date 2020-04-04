package racoony.software.klubi.event_sourcing

import java.util.UUID
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import racoony.software.klubi.ports.bus.EventBus
import racoony.software.klubi.ports.store.EventStore

class AggregateRepository<T : Aggregate>(
    private val eventStore: EventStore,
    private val eventBus: EventBus
) {
    fun findById(id: UUID, clazz: KClass<T>): T {
        val history = this.eventStore.loadEvents(id)

        return clazz.createInstance().apply {
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
