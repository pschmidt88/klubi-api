package racoony.software.klubi.adapter.mongodb.events

import arrow.core.some
import com.mongodb.client.model.Filters.eq
import com.mongodb.kotlin.client.coroutine.MongoClient
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import racoony.software.klubi.event_sourcing.BaseEventStore.EventDescriptor
import racoony.software.klubi.event_sourcing.TestEvent
import kotlin.uuid.Uuid

@QuarkusTest
class MongoDbEventStoreTest {

    @Inject
    lateinit var client: MongoClient


    @Test
    fun `testing stuff`() = runTest {
        val collection = client
            .getDatabase("klubi")
            .getCollection("event_store", EventDescriptor::class.java)

        val random = Uuid.random()

        val document = EventDescriptor(random, 0L, TestEvent("Somevalue", 0L))

        collection.insertOne(document)

        collection.find(eq("aggregateId", random), EventDescriptor::class.java)
            .toList()
            .asIterable()
            .some()


    }

}