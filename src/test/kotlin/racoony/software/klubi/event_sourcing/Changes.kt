package racoony.software.klubi.event_sourcing

class Changes(
    private val aggregate: Aggregate
) {

    companion object {
        fun madeTo(aggregate: Aggregate): Changes {
            return Changes(aggregate)
        }
    }

    fun <T : Event> ofType(eventClass: Class<T>): List<T> {
        return aggregate.changes
            .filterIsInstance(eventClass)
            .toList()
    }
}
