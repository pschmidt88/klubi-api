package racoony.software.klubi.ports.bus

import racoony.software.klubi.event_sourcing.Event

class RecordingEventBus : EventBus {
    private val publishedEvents: MutableList<Event> = mutableListOf()

    override fun publish(event: Event) {
        publishedEvents.add(event)
    }

    override fun <E : Event> subscribe(eventType: Class<E>, handler: EventHandler<E>) {
        TODO("not implemented")
    }

    fun publishedEvents(): List<Event> {
        return publishedEvents
    }
}
