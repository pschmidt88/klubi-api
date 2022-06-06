package racoony.software.klubi.event_sourcing

import racoony.software.klubi.domain.DomainEvent

abstract class Aggregate<T : Any>(
    var id: T
) {
    val changes: MutableList<Event> = mutableListOf()

    val events: MutableList<DomainEvent> = mutableListOf()

    protected fun raise(event: DomainEvent) {
        applyDomainEvents(event)
        events.add(event)
    }

    private fun applyDomainEvents(vararg events: DomainEvent) {
        applyDomainEvents(events.toList())
    }

    abstract fun applyDomainEvents(events: List<DomainEvent>)

    protected fun raise(event: Event) {
        applyChange(event)
        this.changes.add(event)
    }

    private fun applyChange(event: Event) {
        try {
            this.javaClass.getDeclaredMethod("apply", event.javaClass).also {
                it.isAccessible = true
                it.invoke(this, event)
            }
        } catch (_: NoSuchMethodException) {
            // aggregate doesn't care about the event, so we don't
        }
    }

    fun fromHistory(history: List<Event>) {
        history.forEach {
            applyChange(it)
        }
    }
}
