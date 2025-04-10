package racoony.software.klubi.adapter.mongodb.events

import arrow.core.Option
import arrow.core.some
import com.mongodb.client.model.Filters.eq
import com.mongodb.kotlin.client.coroutine.MongoClient
import jakarta.enterprise.context.ApplicationScoped
import kotlinx.coroutines.flow.toList
import racoony.software.klubi.event_sourcing.AggregateId
import racoony.software.klubi.event_sourcing.BaseEventStore
import racoony.software.klubi.ports.bus.EventBus

@ApplicationScoped
class MongoDbEventStore(
    client: MongoClient,
    eventBus: EventBus
) : BaseEventStore(eventBus) {

    private val collection = client
        .getDatabase("klubi")
        .getCollection("event_store", EventDescriptor::class.java)

    override suspend fun stream(key: AggregateId): Option<Iterable<EventDescriptor>> {
        return collection.find(eq("aggregateId", key))
            .toList()
            .asIterable()
            .some()
    }

    override suspend fun appendEventDescriptor(key: AggregateId, eventDescriptor: EventDescriptor) {
//        val document = Document().apply {
//            append("aggregateId", key)
//            append("version", eventDescriptor.version)
//            append("event", eventDescriptor.event)
//        }

        collection.insertOne(eventDescriptor)
    }

}
