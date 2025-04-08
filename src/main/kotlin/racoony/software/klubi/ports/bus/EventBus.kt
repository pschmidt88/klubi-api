package racoony.software.klubi.ports.bus

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import racoony.software.klubi.event_sourcing.Event
import kotlin.reflect.KClass

interface EventBus {
    suspend fun publish(event: Event)
    fun <E : Event> subscribe(eventType: KClass<E>, handler: EventHandler<E>)
}

fun EventBus.publishBlocking(event: Event) = runBlocking {
    publish(event)
}

fun EventBus.publishAsync(event: Event, scope: CoroutineScope = CoroutineScope(Dispatchers.Default)) {
    scope.launch { publish(event) }
}