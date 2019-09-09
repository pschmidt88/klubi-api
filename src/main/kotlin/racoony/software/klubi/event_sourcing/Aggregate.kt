package racoony.software.klubi.event_sourcing

import java.util.UUID

abstract class Aggregate(
    var id: UUID = UUID.randomUUID()
) {
    val changes: MutableList<Event> = mutableListOf()

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
