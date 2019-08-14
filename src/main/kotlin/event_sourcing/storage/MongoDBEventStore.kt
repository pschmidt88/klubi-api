package racoony.software.klubi.event_sourcing.storage

import com.mongodb.MongoClient

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.litote.kmongo.eq
import racoony.software.klubi.event_sourcing.Event
import racoony.software.klubi.event_sourcing.EventStore
import java.util.UUID
import org.litote.kmongo.getCollection

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

data class MongoEvent(
    val aggregateId: UUID,
    val event: Event
) {
    @BsonId
    val id: ObjectId = ObjectId.get()
}
