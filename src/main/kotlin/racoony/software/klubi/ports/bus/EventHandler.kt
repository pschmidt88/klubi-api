package racoony.software.klubi.ports.bus

import racoony.software.klubi.event_sourcing.Event

interface EventHandler<T : Event> {
    fun handle(event: T)
}
