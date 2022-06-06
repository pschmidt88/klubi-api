package racoony.software.klubi.event_sourcing

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class AggregateTest {

    @Test
    fun `event can be raised and applied`() {
        val aggregate = TestAggregate().apply {
            raiseTestEvent()
        }

        aggregate.testEvent shouldBe "foo"
    }

    @Test
    fun `multiple events can be raised and applied`() {
        val aggregate = TestAggregate().apply {
            raiseTestEvent()
            raiseAnotherEvent()
        }

        aggregate.testEvent shouldBe "foo"
        aggregate.anotherEvent shouldBe "bar"
    }

    @Test
    fun `events can be applied from history`() {
        val aggregate = TestAggregate().apply {
            fromHistory(listOf(TestEvent("foo"), AnotherEvent("bar")))
        }

        aggregate.testEvent shouldBe "foo"
        aggregate.anotherEvent shouldBe "bar"
    }
}
