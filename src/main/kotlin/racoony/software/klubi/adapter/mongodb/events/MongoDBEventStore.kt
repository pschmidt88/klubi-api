package racoony.software.klubi.adapter.mongodb.events

import arrow.core.Option
import arrow.core.some
import com.mongodb.client.model.Filters.eq
import io.quarkus.mongodb.reactive.ReactiveMongoClient
import io.smallrye.mutiny.coroutines.awaitSuspending
import jakarta.enterprise.context.ApplicationScoped
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import racoony.software.klubi.event_sourcing.AggregateId
import racoony.software.klubi.event_sourcing.BaseEventStore
import racoony.software.klubi.event_sourcing.Event
import racoony.software.klubi.event_sourcing.EventDescriptor
import racoony.software.klubi.ports.bus.EventBus

@ApplicationScoped
class MongoDbEventStore(
    client: ReactiveMongoClient,
    eventBus: EventBus
) : BaseEventStore<MongoDbEventDescriptor>(eventBus) {

    private val collection = client
        .getDatabase("klubi")
        .getCollection("event_store", MongoDbEventDescriptor::class.java)

    override suspend fun stream(key: AggregateId): Option<Iterable<MongoDbEventDescriptor>> {
        return collection.find(eq("aggregateId", key))
            .collect().asList().awaitSuspending()
            .asIterable()
            .some()
    }

    override suspend fun appendEvent(key: AggregateId, version: Long, event: Event) {
        collection.insertOne(MongoDbEventDescriptor(aggregateId = key, version = version, event = event)).awaitSuspending()
    }
}

data class MongoDbEventDescriptor(
    @BsonId
    val id: ObjectId? = null,
    override val aggregateId: AggregateId,
    override val version: Long,
    override val event: Event,
) : EventDescriptor {}
