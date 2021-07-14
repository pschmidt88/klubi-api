package racoony.software.klubi.adapter.mongodb

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import org.bson.UuidRepresentation
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo
import org.litote.kmongo.reactivestreams.getCollectionOfName
import racoony.software.klubi.MongoDbConfiguration
import racoony.software.klubi.event_sourcing.Event
import racoony.software.klubi.ports.store.EventStore
import java.util.UUID
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class MongoDBEventStore(
    mongoDbConfiguration: MongoDbConfiguration
) : EventStore {

    private val clientSettings = MongoClientSettings.builder()
        .applyConnectionString(ConnectionString(mongoDbConfiguration.connectionString()))
        .uuidRepresentation(UuidRepresentation.JAVA_LEGACY)
        .build()

    private val client = KMongo.createClient(clientSettings)

    private val collection = client
        .getDatabase("klubi")
        .getCollectionOfName<MongoEvent>("event_store")

    override fun save(aggregateId: UUID, events: List<Event>): Uni<Void> {
        val insertMany = this.collection.insertMany(events.map {
            MongoEvent(aggregateId, it)
        })

        return Uni.createFrom().publisher(insertMany).onItem().ignore().andContinueWithNull();
    }

    override fun loadEvents(aggregateId: UUID): Multi<Event> {
        val eventPublisher = this.collection.find(MongoEvent::aggregateId eq aggregateId)
        return Multi.createFrom().publisher(eventPublisher)
            .onItem().transform { it.event }
    }
}