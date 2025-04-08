package racoony.software.klubi.event_sourcing

class Changes(
    private val aggregateRoot: AggregateRoot
) {

    companion object {
        fun madeTo(aggregateRoot: AggregateRoot): Changes {
            return Changes(aggregateRoot)
        }
    }

    fun <T : Event> ofType(eventClass: Class<T>): List<T> {
        return aggregateRoot.uncommittedChanges
            .filterIsInstance(eventClass)
            .toList()
    }
}
