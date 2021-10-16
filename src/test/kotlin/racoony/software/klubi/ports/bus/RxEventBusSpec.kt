package racoony.software.klubi.ports.bus

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import racoony.software.klubi.adapter.rx.RxEventBus
import racoony.software.klubi.event_sourcing.TestEvent

class RxEventBusSpec {

    @Test
    fun `publishes events to subscribed event handlers`() {
        val handler = BusTestEventHandler()
        RxEventBus().apply {
            subscribe(BusTestEvent::class.java, handler)
            publish(BusTestEvent("test"))
        }

        handler.message shouldBe "foo"
    }

    @Test
    fun `does not publish events to other handlers`() {
        val handler = BusTestEventHandler()
        RxEventBus().apply {
            subscribe(BusTestEvent::class.java, handler)
            publish(TestEvent("foo"))
        }

        handler.message shouldBe ""
    }
}
