package racoony.software.klubi.adapter.mongodb

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import org.bson.UuidRepresentation
import org.litote.kmongo.KMongo
import org.litote.kmongo.eq
import org.litote.kmongo.getCollection
import racoony.software.klubi.MongoDbConfiguration
import racoony.software.klubi.event_sourcing.Event
import racoony.software.klubi.ports.store.EventStore
import java.util.UUID
import javax.inject.Singleton

@Singleton
class MongoDBEventStore(
    mongoDbConfiguration: MongoDbConfiguration
) : EventStore {

    private val clientSettings = MongoClientSettings.builder()
            .applyConnectionString(ConnectionString(mongoDbConfiguration.connectionString))
            .uuidRepresentation(UuidRepresentation.JAVA_LEGACY)
            .build()

    private val client = KMongo.createClient(clientSettings)

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