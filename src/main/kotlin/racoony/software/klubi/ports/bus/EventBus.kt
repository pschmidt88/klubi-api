package racoony.software.klubi.ports.bus

import racoony.software.klubi.event_sourcing.Event

interface EventBus {
    fun publish(event: Event)
    fun <E : Event> subscribe(eventType: Class<E>, handler: EventHandler<E>)
}
