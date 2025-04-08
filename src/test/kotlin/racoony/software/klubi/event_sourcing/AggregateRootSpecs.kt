package racoony.software.klubi.event_sourcing

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AggregateRootSpecs {

    private lateinit var aggregateInstance: TestAggregate

    @BeforeEach
    internal fun setUp() {
        this.aggregateInstance = TestAggregate()
    }

    @Test
    fun `single event can be raised and be applied`() {
        aggregateInstance.raiseTestEvent()
        aggregateInstance.testEvent shouldBe "foo"
    }

    @Test
    fun `multiple events can be raised and be applied`() {
        aggregateInstance.raiseTestEvent()
        aggregateInstance.raiseAnotherEvent()
        aggregateInstance.testEvent shouldBe "foo"
        aggregateInstance.anotherEvent shouldBe "bar"
    }

    @Test
    fun `events can be applied from history`() {
        aggregateInstance.fromHistory(listOf(TestEvent("foo"), AnotherEvent("bar")))
        aggregateInstance.testEvent shouldBe "foo"
        aggregateInstance.anotherEvent shouldBe "bar"
    }
}

