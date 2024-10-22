package racoony.software.klubi.adapter.rx

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import racoony.software.klubi.event_sourcing.TestEvent
import racoony.software.klubi.ports.bus.BusTestEvent
import racoony.software.klubi.ports.bus.BusTestEventHandler


//class RxEventBusSpec {
//
//    @Test
//    fun `it publishes event to subscribed event handlers`() {
//        val handler = BusTestEventHandler()
//        RxEventBus().apply {
//            subscribe(BusTestEvent::class.java, handler)
//            publish(BusTestEvent("test"))
//        }
//
//        handler.message shouldBe "foo"
//    }
//
//    @Test
//    fun `it does not publish events to other handlers`() {
//        val handler = BusTestEventHandler()
//        RxEventBus().apply {
//            subscribe(BusTestEvent::class.java, handler)
//            publish(TestEvent("foo"))
//        }
//
//        handler.message shouldBe ""
//    }
//}
