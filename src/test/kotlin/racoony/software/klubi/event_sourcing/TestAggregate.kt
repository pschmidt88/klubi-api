package racoony.software.klubi.event_sourcing

import racoony.software.klubi.domain.DomainEvent
import java.util.UUID

class TestAggregate : Aggregate<UUID>(UUID.randomUUID()) {
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

    override fun applyDomainEvents(events: List<DomainEvent>) {
        TODO("Not yet implemented")
    }
}