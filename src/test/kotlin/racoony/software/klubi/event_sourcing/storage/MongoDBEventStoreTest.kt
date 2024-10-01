package racoony.software.klubi.event_sourcing.storage

import io.kotest.common.runBlocking
import io.kotest.matchers.collections.beEmpty
import io.kotest.matchers.should
import io.kotest.matchers.shouldNot
import io.kotest.matchers.types.beOfType
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import kotlinx.coroutines.flow.toList
import org.junit.jupiter.api.Test
import racoony.software.klubi.adapter.mongodb.events.MongoDBEventStore
import racoony.software.klubi.event_sourcing.TestEvent
import java.util.UUID

@QuarkusTest
class MongoDBEventStoreTest {

    @Inject
    lateinit var eventStore: MongoDBEventStore

    @Test
    fun `writing events to mongodb should not blow up`() {
        val aggregateId = UUID.randomUUID()

        runBlocking {
            eventStore.save(aggregateId, listOf(TestEvent("foo")))
        }
    }

    @Test
    fun `it should read saved events`() {
        val aggregateId = UUID.randomUUID()
        val events = runBlocking {
            eventStore.save(aggregateId, listOf(TestEvent("foo")))
            eventStore.loadEvents(aggregateId).toList()
        }

        events shouldNot beEmpty()
        events.first() should beOfType(TestEvent::class)
    }

}
