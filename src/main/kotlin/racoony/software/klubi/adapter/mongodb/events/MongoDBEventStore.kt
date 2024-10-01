package racoony.software.klubi.adapter.mongodb.events

import com.mongodb.client.model.Filters.eq
import io.quarkus.mongodb.reactive.ReactiveMongoClient
import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
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

    override fun save(aggregateId: UUID, events: List<Event>): Uni<Void> {
        return collection
            .insertMany(events.map {
                MongoEvent(aggregateId = aggregateId, event = it)
            })
            .onItem().ignore().andContinueWithNull()
    }

    override fun loadEvents(aggregateId: UUID): Multi<Event> {
        return collection
            .find(eq("aggregateId", aggregateId))
            .onItem().transform { it.event }
    }
}