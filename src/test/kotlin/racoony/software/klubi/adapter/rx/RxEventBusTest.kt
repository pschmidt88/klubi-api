package racoony.software.klubi.adapter.rx

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import racoony.software.klubi.adapter.bus.rx.RxEventBus
import racoony.software.klubi.event_sourcing.TestEvent
import racoony.software.klubi.ports.bus.BusTestEvent
import racoony.software.klubi.ports.bus.BusTestEventHandler
import racoony.software.klubi.ports.bus.publishBlocking


class RxEventBusSpec {

    @Test
    fun `it publishes event to subscribed event handlers`() {
        val handler = BusTestEventHandler()
        with(RxEventBus()) {
            subscribe(BusTestEvent::class, handler)
            publishBlocking(BusTestEvent("test"))
        }

        handler.message shouldBe "foo"
    }

    @Test
    fun `it does not publish events to other handlers`() {
        val handler = BusTestEventHandler()
        with(RxEventBus()) {
            subscribe(BusTestEvent::class, handler)
            publishBlocking(TestEvent("foo"))
        }

        handler.message shouldBe ""
    }
}