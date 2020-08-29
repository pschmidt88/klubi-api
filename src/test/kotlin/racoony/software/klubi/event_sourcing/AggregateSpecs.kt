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
                    aggregateInstance.foo shouldBe "bar"
                }

                it("multiple events can be raised and be applied") {
                    aggregateInstance.raiseTestEvent()
                    aggregateInstance.raiseAnotherEvent()
                    aggregateInstance.foo shouldBe "barbaz"
                }
            }

            context("replaying events") {
                it("events can be applied from history") {
                    aggregateInstance.fromHistory(listOf(TestEvent(), AnotherEvent()))
                    aggregateInstance.foo shouldBe "barbaz"
                }
            }
        }
    }
}

class TestAggregate : Aggregate() {
    var foo: String = ""

    fun raiseTestEvent() {
        this.raise(TestEvent())
    }

    @Suppress("unused")
    private fun apply(testEvent: TestEvent) {
        this.foo += "bar"
    }

    @Suppress("unused")
    private fun apply(anotherEvent: AnotherEvent) {
        this.foo += "baz"
    }

    fun raiseAnotherEvent() {
        this.raise(AnotherEvent())
    }
}

class TestEvent : Event

class AnotherEvent : Event
