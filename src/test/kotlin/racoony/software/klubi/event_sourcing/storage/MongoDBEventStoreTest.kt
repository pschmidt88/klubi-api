package racoony.software.klubi.event_sourcing.storage

import arrow.core.some
import io.kotest.common.runBlocking
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.beOfType
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Test
import racoony.software.klubi.adapter.mongodb.events.MongoDbEventStore
import racoony.software.klubi.event_sourcing.TestEvent
import java.util.UUID

@QuarkusTest
class MongoDBEventStoreTest {

    // TODO: replace runBlocking with runTest
    // TODO: Instantiate MongoDbEventSTore directly and replace eventBus with fake

    @Inject
    lateinit var eventStore: MongoDbEventStore

    @Test
    fun `writing events to mongodb should not blow up`() {
        val aggregateId = UUID.randomUUID()

        runBlocking {
            eventStore.saveEvents(aggregateId, listOf(TestEvent("foo")), 0L.some())
        }
    }

    @Test
    fun `it should read saved events`() {
        val aggregateId = UUID.randomUUID()
        val events = runBlocking {
            eventStore.saveEvents(aggregateId, listOf(TestEvent("foo")), 0L.some())
            eventStore.findEventsByAggregateId(aggregateId)
        }

        // TODO: strikt or arrow assertions for kotest
        events.isSome() shouldBe true
        events.getOrNull()?.first() should beOfType(TestEvent::class)
    }

}
