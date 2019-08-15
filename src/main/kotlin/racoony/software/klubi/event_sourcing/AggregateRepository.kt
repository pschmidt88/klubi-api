package racoony.software.klubi.event_sourcing

import racoony.software.klubi.racoony.software.klubi.event_sourcing.Aggregate
import java.util.UUID
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

class AggregateRepository<T : Aggregate>(
    private val eventStore: EventStore
) {
    fun findById(id: UUID, clazz: KClass<T>): T {
        val history = this.eventStore.loadEvents(id)

        return clazz.createInstance().apply {
            fromHistory(history)
        }
    }

    fun save(aggregate: T) {
        this.eventStore.save(aggregate.id, aggregate.changes)
    }
}