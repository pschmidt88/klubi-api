package racoony.software.klubi.event_sourcing.storage

import io.kotest.matchers.collections.beEmpty
import io.kotest.matchers.should
import io.kotest.matchers.shouldNot
import io.kotest.matchers.types.beOfType
import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.Test
import racoony.software.klubi.adapter.mongodb.events.MongoDBEventStore
import racoony.software.klubi.event_sourcing.TestEvent
import java.util.UUID
import javax.inject.Inject

@QuarkusTest
class MongoDBEventStoreTest {

    @Inject
    lateinit var eventStore: MongoDBEventStore

    @Test
    fun `writing events to mongodb should not blow up`() {
        val aggregateId = UUID.randomUUID()

        eventStore.save(aggregateId, listOf(TestEvent("foo"))).await().indefinitely()
    }

    @Test
    fun `it should read saved events`() {
        val aggregateId = UUID.randomUUID()
        eventStore.save(aggregateId, listOf(TestEvent("foo"))).await().indefinitely()

        val events = eventStore.loadEvents(aggregateId).collect().asList().await().indefinitely()

        events shouldNot beEmpty()
        events.first() should beOfType(TestEvent::class)
    }

}
