package racoony.software.klubi.event_sourcing

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