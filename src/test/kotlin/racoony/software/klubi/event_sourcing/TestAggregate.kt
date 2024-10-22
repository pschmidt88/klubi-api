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

    override fun applyChange(event: Event) {
        when (event) {
            is TestEvent -> apply(event)
            is AnotherEvent -> apply(event)
        }
    }
}

class AnotherEvent(
    val otherValue: String
) : Event()

class TestEvent(
    val someValue: String
) : Event()
