package racoony.software.klubi.event_sourcing

@DomainEvent
class AnotherEvent(
    val otherValue: String
) : Event()