package racoony.software.klubi.event_sourcing.bus

import racoony.software.klubi.event_sourcing.Event

interface EventHandler<T : Event> {
    fun handle(event: T)
}
