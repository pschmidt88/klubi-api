package racoony.software.klubi.event_sourcing

abstract class Aggregate {
    internal val recordedEvents: MutableList<Event> = mutableListOf()

    protected fun raise(event: Event) {
        applyEventToAggregate(event)
        this.recordedEvents.add(event)
    }

    private fun applyEventToAggregate(event: Event) {
        this.javaClass.getDeclaredMethod("apply", event.javaClass).also {
            it.isAccessible = true
            it.invoke(this, event)
        }
    }
}