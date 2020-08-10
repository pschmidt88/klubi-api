package racoony.software.klubi.adapter.mongodb

import com.mongodb.client.MongoClient
import org.litote.kmongo.eq
import org.litote.kmongo.getCollection
import racoony.software.klubi.event_sourcing.Event
import racoony.software.klubi.ports.store.EventStore
import java.util.UUID

class MongoDBEventStore(
    client: MongoClient
) : EventStore {
    private val collection = client
        .getDatabase("klubi")
        .getCollection<MongoEvent>("event_store")

    override fun save(aggregateId: UUID, events: List<Event>) {
        this.collection.insertMany(events.map {
            MongoEvent(aggregateId, it)
        })
    }

    override fun loadEvents(aggregateId: UUID): List<Event> {
        return this.collection
            .find(MongoEvent::aggregateId eq aggregateId)
            .map(MongoEvent::event)
            .toList()
    }
}