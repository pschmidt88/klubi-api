package racoony.software.klubi.adapter.bus.rx

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import jakarta.enterprise.context.ApplicationScoped
import racoony.software.klubi.event_sourcing.Event
import racoony.software.klubi.ports.bus.EventBus
import racoony.software.klubi.ports.bus.EventHandler
import kotlin.reflect.KClass

@Deprecated("Use CoroutineEventBus")
class RxEventBus : EventBus {
    private val publisher = PublishSubject.create<Event>()

    private fun <E : Event> listen(eventType: KClass<E>): Observable<E> = publisher.ofType(eventType.java)

    override suspend fun publish(event: Event) {
        publisher.onNext(event)
    }

    override fun <E : Event> subscribe(eventType: KClass<E>, handler: EventHandler<E>) {
        listen(eventType).apply {
            subscribe { event ->
                handler.handle(event)
            }
            onErrorReturn { throw it }
        }
    }
}
