package racoony.software.klubi.adapter.rx

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import racoony.software.klubi.event_sourcing.Event
import racoony.software.klubi.ports.bus.EventBus
import racoony.software.klubi.ports.bus.EventHandler
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class RxEventBus : EventBus {
    private val publisher = PublishSubject.create<Event>()

    private fun <E : Event> listen(eventType: Class<E>): Observable<E> = publisher.ofType(eventType)

    override fun publish(event: Event) {
        publisher.onNext(event)
    }

    override fun <E : Event> subscribe(eventType: Class<E>, handler: EventHandler<E>) {
        listen(eventType).subscribe {
            handler.handle(it)
        }
    }
}