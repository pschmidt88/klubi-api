package racoony.software.klubi.event_sourcing

@DomainEvent
class TestEvent(
    val someValue: String
) : Event()