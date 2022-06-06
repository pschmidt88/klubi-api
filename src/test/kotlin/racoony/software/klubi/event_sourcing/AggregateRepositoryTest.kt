package racoony.software.klubi.event_sourcing

import com.mongodb.client.MongoClient
import com.mongodb.client.model.Filters.eq
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.quarkus.test.junit.QuarkusTest
import io.vertx.core.eventbus.EventBus
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import racoony.software.klubi.adapter.mongodb.events.MongoEvent
import racoony.software.klubi.ports.bus.RecordingEventBus
import racoony.software.klubi.ports.store.EventStore
import java.util.UUID
import javax.inject.Inject

@QuarkusTest
class AggregateRepositoryTest {

    @Inject
    lateinit var eventStore: EventStore

    @Inject
    lateinit var eventBus: EventBus

    @Inject
    lateinit var mongoClient: MongoClient

    @AfterEach
    internal fun cleanupDatabase() {
        mongoClient.getDatabase("klubi").getCollection("event_store").drop()
    }

    @Test
    fun `Given Aggregate with at least one event in store when findById then aggregate is not null`() {
        val aggregateId = UUID.randomUUID()
        eventStore.save(aggregateId, listOf(TestEvent("foo"))).await().indefinitely()

        val aggregate = AggregateRepository<TestAggregate>(eventStore, eventBus)
            .findById(aggregateId) { TestAggregate() }
            .await().indefinitely()

        aggregate shouldNotBe null
        aggregate.testEvent shouldBe "foo"
    }

    @Test
    fun `aggregate with raised event, when saving aggregate, events should've been persisted to the event store`() {
        val aggregateId = UUID.randomUUID()
        val testAggregate = TestAggregate().apply {
            id = aggregateId
            raiseTestEvent()
        }

        AggregateRepository<TestAggregate>(eventStore, eventBus).save(testAggregate)
            .await().indefinitely()

        val events = mongoClient.getDatabase("klubi")
            .getCollection("event_store", MongoEvent::class.java)
            .find(eq("aggregateId", aggregateId))
            .toList()

        events shouldNotBe null
        events.size shouldBe 1
    }

    @Test
    fun `aggregate with raised event, when saving aggregate, events should've been published to event bus`() {
        val aggregateId = UUID.randomUUID()
        val testAggregate = TestAggregate().apply {
            id = aggregateId
            raiseTestEvent()
        }

        val recordingEventBus = RecordingEventBus()
        AggregateRepository<TestAggregate>(eventStore, recordingEventBus).save(testAggregate)
            .await().indefinitely()

        recordingEventBus.publishedEventsOfType(TestEvent::class.java) shouldHaveSize 1
    }
}
