package racoony.software.klubi.ports.bus

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestScope
import racoony.software.klubi.event_sourcing.Event
import kotlin.reflect.KClass

class RecordingEventBus(
    scope: CoroutineScope = TestScope()
) : EventBus {
    private val testScheduler: TestCoroutineScheduler = (scope as TestScope).testScheduler

    private val publishedEvents: MutableList<Event> = mutableListOf()

    override suspend fun publish(event: Event) {
        publishedEvents += event
        testScheduler.advanceUntilIdle()
    }

    override fun <E : Event> subscribe(eventType: KClass<E>, handler: EventHandler<E>) {
        // Not necessary right now for tests
    }

    fun publishedEvents(): List<Event> {
        return publishedEvents
    }

    fun <E: Event> publishedEventsOfType(eventType: KClass<E>): List<E> {
        return publishedEvents.filterIsInstance(eventType.java)
    }
}
