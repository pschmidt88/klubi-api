package racoony.software.klubi.adapter.bus

import jakarta.enterprise.context.ApplicationScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import racoony.software.klubi.event_sourcing.Event
import racoony.software.klubi.ports.bus.EventBus
import racoony.software.klubi.ports.bus.EventHandler
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

@ApplicationScoped
class CoroutineEventBus(
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
) : EventBus {

    private val channels = ConcurrentHashMap<KClass<out Event>, MutableList<Channel<Event>>>()

    override suspend fun publish(event: Event) {
        channels[event::class]?.forEach { it.send(event) }
    }

    override fun <E : Event> subscribe(eventType: KClass<E>, handler: EventHandler<E>) {
        val channel = Channel<Event>(Channel.UNLIMITED)
        channels.computeIfAbsent(eventType) { mutableListOf() }.add(channel)

        scope.launch {
            channel.consumeEach { event: Event -> handler.handle(event as E) }
        }
    }
}
