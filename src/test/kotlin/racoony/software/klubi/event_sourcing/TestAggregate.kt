package racoony.software.klubi.event_sourcing

import io.quarkus.logging.Log
import java.util.UUID

class TestAggregate(id: AggregateId = UUID.randomUUID()) : AggregateRoot(id) {

    var testEvent = ""
    var anotherEvent = ""

    fun raiseTestEvent() {
        this.raise(TestEvent("foo"))
    }

    @Suppress("unused")
    private fun applyTestEvent(testEvent: TestEvent) {
        this.testEvent = testEvent.someValue
    }

    @Suppress("unused")
    private fun applyAnotherEvent(anotherEvent: AnotherEvent) {
        this.anotherEvent = anotherEvent.otherValue
    }

    fun raiseAnotherEvent() {
        this.raise(AnotherEvent("bar"))
    }

    override fun applyEvent(event: Event): AggregateRoot {
        Log.debugf("Applying event %s to aggregate %s", event, this)
        when (event) {
            is TestEvent -> applyTestEvent(event)
            is AnotherEvent -> applyAnotherEvent(event)
        }

        return this
    }
}