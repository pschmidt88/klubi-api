package racoony.software.klubi.event_sourcing

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.beOfType
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import racoony.software.klubi.ports.bus.RecordingEventBus
import racoony.software.klubi.ports.store.EventStore
import java.util.UUID

class AggregateRepositorySpec : BehaviorSpec({
    Given("Aggregate with at least one event in store") {
        val aggregateId = UUID.randomUUID()
        val eventStore = InMemoryEventStore().apply { save(aggregateId, listOf(TestEvent())) }

        When("findById") {
            val aggregate = AggregateRepository<TestAggregate>(eventStore,
                RecordingEventBus()
            ).findById(aggregateId, TestAggregate::class)

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
                    AggregateRepository<TestAggregate>(InMemoryEventStore(),
                        RecordingEventBus()
                    ).findById(UUID.randomUUID(), TestAggregate::class)
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
                        AggregateRepository<MoreComplicatedTestAggregate>(eventStore,
                            RecordingEventBus()
                        )
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
            val eventBus = RecordingEventBus()
            AggregateRepository<TestAggregate>(eventStore, eventBus).save(testAggregate)

            Then("events should've been persisted to event store") {
                val events = eventStore.storage[aggregateId]
                events shouldNotBe null
                events?.size shouldBe 1
            }

            Then("events should've been published to event bus") {
                eventBus.publishedEvents() shouldHaveSize 1
                eventBus.publishedEvents().first() should beOfType(TestEvent::class)
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

    @Suppress("UNUSED_PARAMETER")
    fun apply(event: TestEvent) {
        this.foo = "bar"
    }
}
