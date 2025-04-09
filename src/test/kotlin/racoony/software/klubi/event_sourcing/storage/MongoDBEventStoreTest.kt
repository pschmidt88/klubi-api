package racoony.software.klubi.event_sourcing.storage

import arrow.core.some
import io.kotest.common.runBlocking
import io.kotest.inspectors.runTest
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.beOfType
import io.quarkus.mongodb.reactive.ReactiveMongoClient
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import racoony.software.klubi.adapter.mongodb.events.MongoDbEventStore
import racoony.software.klubi.event_sourcing.TestEvent
import racoony.software.klubi.ports.bus.RecordingEventBus
import java.util.UUID

@QuarkusTest
class MongoDBEventStoreTest {

    // TODO: replace runBlocking with runTest
    // TODO: Instantiate MongoDbEventSTore directly and replace eventBus with fake

    @Inject
    lateinit var mongoClient: ReactiveMongoClient

    @Test
    fun `writing events to mongodb should not blow up and return success`() = runTest {
        val aggregateId = UUID.randomUUID()

        val eventStore = MongoDbEventStore(mongoClient, RecordingEventBus())

        assertDoesNotThrow { eventStore.saveEvents(aggregateId, listOf(TestEvent("foo")), 0L.some()) }
    }

    @Test
    fun `it should read saved events`() = runTest {
        val aggregateId = UUID.randomUUID()

        val eventStore = MongoDbEventStore(mongoClient, RecordingEventBus()).also {
            it.saveEvents(aggregateId, listOf(TestEvent("foo")), 0L.some()).isRight() shouldBe true
        }

        val events = eventStore.findEventsByAggregateId(aggregateId)

        // TODO: strikt or arrow assertions for kotest
        events.isSome() shouldBe true
        events.getOrNull()?.first() should beOfType(TestEvent::class)
    }

}
