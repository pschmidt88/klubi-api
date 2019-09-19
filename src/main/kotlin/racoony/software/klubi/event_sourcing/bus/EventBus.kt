package racoony.software.klubi.event_sourcing.bus

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import racoony.software.klubi.event_sourcing.Event
import javax.swing.event.DocumentEvent

class EventBus {
    private val publisher = PublishSubject.create<Event>()

    private fun <E: Event> listen(eventType: Class<E>): Observable<E> = publisher.ofType(eventType)

    fun publish(event: Event) {
        publisher.onNext(event)
    }

    fun <E: Event> subscribe(eventType: Class<E>, handler: EventHandler<E>) {
        this.listen(eventType).subscribe {
            handler.handle(it)
        }
    }
}
