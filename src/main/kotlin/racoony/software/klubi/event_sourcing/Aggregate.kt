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

    abstract fun applyChange(event: Event)

    fun fromHistory(history: List<Event>) {
        history.forEach {
            applyChange(it)
        }
    }
}
