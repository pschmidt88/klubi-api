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

//            it("should have an id") {
//                assertThat(aggregateInstance).hasFieldOrProperty("id")
//                assertThat(aggregateInstance.id).isNotNull()
//            }

            context("raising events") {

                it("event can be raised and be applied") {
                    aggregateInstance.someCommandWhichRaisesAnEvent()
                    aggregateInstance.foo shouldBe "bar"
                }

//                it("multiple events can be raised and be applied") {
//                    aggregateInstance.raiseEvent(TestEvent(), TestEvent())
//                    assertThat(aggregateInstance.foo).isEqualTo("barbar")
//                }
            }

            context("occurred events") {
                it("has a list of occurred events") {
                    aggregateInstance.someCommandWhichRaisesAnEvent()
                    aggregateInstance.recordedEvents shouldNot beEmpty()
                }
            }
        }
    }
}

class TestAggregate : Aggregate() {
    var foo: String = ""

    fun someCommandWhichRaisesAnEvent() {
        this.raise(TestEvent())
    }

    @Suppress("unused")
    fun apply(testEvent: TestEvent) {
        this.foo += "bar"
    }
}

class TestEvent : Event()
