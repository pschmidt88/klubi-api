package racoony.software.klubi.adapter.mongodb.events

import arrow.core.Option
import arrow.core.some
import com.mongodb.client.model.Filters.eq
import io.quarkus.mongodb.reactive.ReactiveMongoClient
import io.smallrye.mutiny.coroutines.asFlow
import io.smallrye.mutiny.coroutines.awaitSuspending
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import org.bson.Document
import racoony.software.klubi.event_sourcing.AggregateId
import racoony.software.klubi.event_sourcing.BaseEventStore
import racoony.software.klubi.event_sourcing.Event
import racoony.software.klubi.event_sourcing.EventStore
import racoony.software.klubi.ports.bus.EventBus
import java.util.UUID

@ApplicationScoped
class MongoDbEventStore(
    client: ReactiveMongoClient,
    eventBus: EventBus
) : BaseEventStore(eventBus) {

    private val collection = client
        .getDatabase("klubi")
        .getCollection("event_store")

//    override suspend fun save(aggregateId: UUID, events: Iterable<Event>): Result<Unit> = runCatching {
//        collection.insertMany(events.map {
//            MongoEvent(aggregateId = aggregateId, event = it)
//        }).awaitSuspending()
//    }
//
//    override suspend fun loadEvents(aggregateId: UUID): Flow<Event> {
//        return collection
//            .find(eq("aggregateId", aggregateId))
//            .map { it.event }
//            .asFlow()
//    }

    override suspend fun stream(key: AggregateId): Option<Iterable<EventDescriptor>> {
        return collection.find(eq("aggregateId", key)).collect().asList().awaitSuspending().map {
            document ->  EventDescriptor(key, document.getLong("version"), document.get("event", Event::class.java))
        }.asIterable().some()
    }

    override suspend fun appendEventDescriptor(key: AggregateId, eventDescriptor: EventDescriptor) {
        val document = Document().apply {
            append("aggregateId", key)
            append("version", eventDescriptor.version)
            append("event", eventDescriptor.event)
        }

        collection.insertOne(document).awaitSuspending()
    }

}