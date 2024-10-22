package racoony.software.klubi.adapter.eventbus

import racoony.software.klubi.event_sourcing.Event
import racoony.software.klubi.ports.bus.EventBus

class VertXEventBus(
    private val eventBus: io.vertx.mutiny.core.eventbus.EventBus
) : EventBus {
    override suspend fun publish(event: Event) {
        eventBus.publish(event::class.qualifiedName, event)
    }
}