package racoony.software.klubi.adapter.mongodb.events

import arrow.core.some
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.quarkus.mongodb.reactive.ReactiveMongoClient
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import racoony.software.klubi.event_sourcing.TestEvent
import racoony.software.klubi.ports.bus.RecordingEventBus
import kotlin.uuid.Uuid

@QuarkusTest
class MongoDbEventStoreTest {

    @Inject
    lateinit var client: ReactiveMongoClient

    @Test
    fun `testing stuff`() = runTest {
        val store = MongoDbEventStore(client, RecordingEventBus())

        val random = Uuid.random()
        val writeResult = store.saveEvents(random, listOf(TestEvent("foo")), 0L.some())

        writeResult.isRight() shouldBe true

        val readResult = store.findEventsByAggregateId(random)
        readResult.isSome() shouldBe true
        readResult.getOrNull() shouldNotBe null
        readResult.getOrNull()?.first() shouldBe TestEvent("foo", 0L)
    }

}
