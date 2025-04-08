package racoony.software.klubi.adapter.mongodb.events

import com.mongodb.client.model.Filters.eq
import io.quarkus.mongodb.reactive.ReactiveMongoClient
import io.smallrye.mutiny.coroutines.asFlow
import io.smallrye.mutiny.coroutines.awaitSuspending
import jakarta.enterprise.context.ApplicationScoped
import kotlinx.coroutines.flow.Flow
import racoony.software.klubi.event_sourcing.Event
import racoony.software.klubi.ports.store.EventStore
import java.util.UUID

@ApplicationScoped
class MongoDBEventStore(
    client: ReactiveMongoClient
) : EventStore {
    private val collection = client
        .getDatabase("klubi")
        .getCollection("event_store", MongoEvent::class.java)

    override suspend fun save(aggregateId: UUID, events: Iterable<Event>): Result<Unit> = runCatching {
        collection.insertMany(events.map {
            MongoEvent(aggregateId = aggregateId, event = it)
        }).awaitSuspending()
    }

    override suspend fun loadEvents(aggregateId: UUID): Flow<Event> {
        return collection
            .find(eq("aggregateId", aggregateId))
            .map { it.event }
            .asFlow()
    }
}