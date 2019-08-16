package racoony.software.klubi.event_sourcing.storage

import io.kotlintest.Spec
import io.kotlintest.matchers.beEmpty
import io.kotlintest.matchers.beOfType
import io.kotlintest.shouldBe
import io.kotlintest.shouldNot
import io.kotlintest.specs.DescribeSpec
import org.litote.kmongo.KMongo
import racoony.software.klubi.KGenericContainer
import racoony.software.klubi.event_sourcing.TestEvent
import java.util.UUID

class MongoDBEventStoreSpec : DescribeSpec() {
    private val mongoContainer: KGenericContainer = KGenericContainer("mongo").apply {
        start()
    }

    override fun afterSpec(spec: Spec) {
        this.mongoContainer.stop()
    }

    init {
        val client = KMongo.createClient(this.mongoContainer.containerIpAddress, this.mongoContainer.getMappedPort(27017))
        val eventStore = MongoDBEventStore(client)

        describe("Writing events to mongodb") {
            val aggregateId = UUID.randomUUID()
            it("should not blow up when saving") {
                eventStore.save(aggregateId, listOf(TestEvent()))
            }

            it("should read saved events") {
                val events = eventStore.loadEvents(aggregateId)
                events shouldNot beEmpty()
                events.first() shouldBe beOfType(TestEvent::class)
            }
        }
    }
}