package racoony.software.klubi.event_sourcing.storage

import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.beEmpty
import io.kotest.matchers.should
import io.kotest.matchers.shouldNot
import io.kotest.matchers.types.beOfType
import racoony.software.klubi.KGenericContainer
import racoony.software.klubi.MongoDbConfiguration
import racoony.software.klubi.adapter.mongodb.MongoDBEventStore
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
        describe("Writing events to mongodb") {
            val configuration = object : MongoDbConfiguration {
                override fun connectionString() = "mongodb://${mongoContainer.containerIpAddress}:${mongoContainer.getMappedPort(27017)}"
            }

            val eventStore = MongoDBEventStore(configuration)
            val aggregateId = UUID.randomUUID()

            it("should not blow up when saving") {
                eventStore.save(aggregateId, listOf(TestEvent())).await().indefinitely()
            }

            it("should read saved events") {
                val events = eventStore.loadEvents(aggregateId).collect().asList().await().indefinitely()

                events shouldNot beEmpty()
                events.first() should beOfType(TestEvent::class)
            }
        }
    }
}
