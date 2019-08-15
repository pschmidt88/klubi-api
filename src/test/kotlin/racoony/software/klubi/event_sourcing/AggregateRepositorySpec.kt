package racoony.software.klubi.event_sourcing

import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.BehaviorSpec
import racoony.software.klubi.racoony.software.klubi.event_sourcing.Aggregate
import java.util.UUID

class AggregateRepositorySpec : BehaviorSpec({
    Given("Aggregate with at least one event in store") {
        val aggregateId = UUID.randomUUID()
        val eventStore = InMemoryEventStore().apply { save(aggregateId, listOf(TestEvent())) }

        When("findById") {
            val aggregate = AggregateRepository<TestAggregate>(eventStore).findById(aggregateId, TestAggregate::class)

            Then("aggregate is not null") {
                aggregate shouldNotBe null
            }

            Then("events were applied") {
                aggregate.foo shouldBe "bar"
            }
        }
    }

    Given("No events for aggregate in event store") {
        When("findById") {
            Then("Aggregate not exists") {
                shouldThrow<AggregateNotExists> {
                    AggregateRepository<TestAggregate>(InMemoryEventStore()).findById(UUID.randomUUID(), TestAggregate::class)
                }
            }
        }
    }

    Given("TestAggregate with no no-arg constructor") {
        val id = UUID.randomUUID()
        And("one event for this aggregate") {
            val eventStore = InMemoryEventStore().apply { save(id, listOf(TestEvent())) }
            When("findById") {
                Then("aggregate class should have no-arg constructor") {
                    shouldThrow<IllegalArgumentException> {
                        AggregateRepository<MoreComplicatedTestAggregate>(eventStore)
                            .findById(id, MoreComplicatedTestAggregate::class)
                    }
                }
            }
        }
    }

    Given("TestAggregate with raised event") {
        val aggregateId = UUID.randomUUID()
        val testAggregate = TestAggregate().apply {
            id = aggregateId
            raiseTestEvent()
        }
        When("Saving aggregate") {
            val eventStore = InMemoryEventStore()
            AggregateRepository<TestAggregate>(eventStore).save(testAggregate)

            Then("events should've been persisted to event store") {
                val events = eventStore.storage[aggregateId]
                events shouldNotBe null
                events?.size shouldBe 1
            }
        }
    }
})

class InMemoryEventStore : EventStore {
    val storage: MutableMap<UUID, MutableList<Event>> = mutableMapOf()

    override fun save(aggregateId: UUID, events: List<Event>) {
        val aggregateEvents = this.storage.getOrPut(aggregateId, { mutableListOf() })
        aggregateEvents.addAll(events)
    }

    override fun loadEvents(aggregateId: UUID): List<Event> {
        if (!storage.containsKey(aggregateId)) {
            throw AggregateNotExists()
        }

        return storage[aggregateId]!!
    }
}

class AggregateNotExists : Exception()

class MoreComplicatedTestAggregate(
    var someParam: String
) : Aggregate() {

    private var foo: String = ""

    @Suppress("unused")
    fun apply(event: TestEvent) {
        this.foo = "bar"
    }
}