package racoony.software.klubi.event_sourcing

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.test.TestCase
import io.kotest.matchers.shouldBe

class AggregateSpecs : DescribeSpec() {
    private lateinit var aggregateInstance: TestAggregate

    override fun beforeTest(testCase: TestCase) {
        this.aggregateInstance = TestAggregate()
    }

    init {
        describe("a test aggregate") {
            context("raising events") {
                it("event can be raised and be applied") {
                    aggregateInstance.raiseTestEvent()
                    aggregateInstance.testEvent shouldBe "foo"
                }

                it("multiple events can be raised and be applied") {
                    aggregateInstance.raiseTestEvent()
                    aggregateInstance.raiseAnotherEvent()
                    aggregateInstance.testEvent shouldBe "foo"
                    aggregateInstance.anotherEvent shouldBe "bar"
                }
            }

            context("replaying events") {
                it("events can be applied from history") {
                    aggregateInstance.fromHistory(listOf(TestEvent("foo"), AnotherEvent("bar")))
                    aggregateInstance.testEvent shouldBe "foo"
                    aggregateInstance.anotherEvent shouldBe "bar"
                }
            }
        }
    }
}

class TestAggregate : Aggregate() {
    var testEvent = ""
    var anotherEvent = ""

    fun raiseTestEvent() {
        this.raise(TestEvent("foo"))
    }

    @Suppress("unused")
    private fun apply(testEvent: TestEvent) {
        this.testEvent = testEvent.someValue
    }

    @Suppress("unused")
    private fun apply(anotherEvent: AnotherEvent) {
        this.anotherEvent = anotherEvent.otherValue
    }

    fun raiseAnotherEvent() {
        this.raise(AnotherEvent("bar"))
    }
}

data class TestEvent(
    val someValue: String
) : Event

data class AnotherEvent(
    val otherValue: String
) : Event
