package racoony.software.klubi.adapter.rx

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import jakarta.enterprise.context.ApplicationScoped
import racoony.software.klubi.event_sourcing.Event
import racoony.software.klubi.ports.bus.EventBus
import racoony.software.klubi.ports.bus.EventHandler

//@ApplicationScoped
//class RxEventBus : EventBus {
//    private val publisher = PublishSubject.create<Event>()
//
//    private fun <E : Event> listen(eventType: Class<E>): Observable<E> = publisher.ofType(eventType)
//
//    override fun publish(event: Event) {
//        publisher.onNext(event)
//    }
//
//    fun <E : Event> subscribe(eventType: Class<E>, handler: EventHandler<E>) {
//        listen(eventType).apply {
//            subscribe { event ->
//                handler.handle(event)
//            }
//            onErrorReturn { throw it }
//        }
//    }
//}