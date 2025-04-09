package racoony.software.klubi.event_sourcing

import arrow.core.some
import com.mongodb.client.MongoClient
import com.mongodb.client.model.Filters.eq
import io.kotest.common.runBlocking
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import racoony.software.klubi.adapter.mongodb.events.MongoEvent
import racoony.software.klubi.ports.bus.RecordingEventBus
import java.util.UUID

@QuarkusTest
class AggregateRootRepositoryTest {

    val testScope = TestScope()

    @Inject
    lateinit var eventStore: EventStore

    @Inject
    lateinit var mongoClient: MongoClient

    @AfterEach
    internal fun cleanupDatabase() {
        mongoClient.getDatabase("klubi").getCollection("event_store").drop()
    }

    @Test
    fun `Given Aggregate with at least one event in store when findById then aggregate is not null`() = runTest {
        val aggregateId = UUID.randomUUID()

        eventStore.saveEvents(aggregateId, listOf(TestEvent("foo")), 0L.some())

        val aggregate = AggregateRepository<TestAggregate>(eventStore).findById(aggregateId) { TestAggregate() }

        aggregate shouldNotBe null
        aggregate.testEvent shouldBe "foo"
    }

    @Test
    fun `aggregate with raised event, when saving aggregate, events should've been persisted to the event store`() {
        val aggregateId = UUID.randomUUID()
        val testAggregate = TestAggregate(aggregateId).apply {
            raiseTestEvent()
        }

        runBlocking {
            AggregateRepository<TestAggregate>(eventStore).save(testAggregate)
        }

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
        val testAggregate = TestAggregate(aggregateId).apply {
            raiseTestEvent()
        }

        val recordingEventBus = RecordingEventBus(testScope)

        runBlocking {
            AggregateRepository<TestAggregate>(eventStore).save(testAggregate)
        }

        recordingEventBus.publishedEventsOfType(TestEvent::class) shouldHaveSize 1
    }
}
