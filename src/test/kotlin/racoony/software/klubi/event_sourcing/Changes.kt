package racoony.software.klubi.event_sourcing

import racoony.software.klubi.racoony.software.klubi.event_sourcing.Aggregate

class Changes(
    private val aggregate: Aggregate
) {

    fun <T : Event> ofType(eventClass: Class<T>): List<T> {
        return aggregate.changes
            .filterIsInstance(eventClass)
            .toList()
    }
}
