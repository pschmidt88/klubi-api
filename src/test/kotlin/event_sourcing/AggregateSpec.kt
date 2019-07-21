package event_sourcing

import io.kotlintest.TestCase
import io.kotlintest.matchers.beEmpty
import io.kotlintest.shouldBe
import io.kotlintest.shouldNot
import io.kotlintest.specs.DescribeSpec
import racoony.software.klubi.event_sourcing.Aggregate
import racoony.software.klubi.event_sourcing.Event


class AggregateSpec : DescribeSpec() {
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

            context("occurred events") {
                it("has a list of occurred events") {
                    aggregateInstance.raiseTestEvent()
                    aggregateInstance.recordedEvents shouldNot beEmpty()
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
    fun apply(testEvent: TestEvent) {
        this.foo += "bar"
    }

    @Suppress
    fun apply(anotherEvent: AnotherEvent) {
        this.foo += "baz"
    }

    fun raiseAnotherEvent() {
        this.raise(AnotherEvent())
    }
}

class TestEvent : Event()

class AnotherEvent : Event()
