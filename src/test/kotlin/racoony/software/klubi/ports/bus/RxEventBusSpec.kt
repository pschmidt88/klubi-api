package racoony.software.klubi.ports.bus

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import racoony.software.klubi.adapter.rx.RxEventBus
import racoony.software.klubi.event_sourcing.Event
import racoony.software.klubi.event_sourcing.TestEvent

class RxEventBusSpec : DescribeSpec({
    describe("event bus") {
        it("publishes events to subscribed event handlers") {
            val handler = BusTestEventHandler()
            RxEventBus().apply {
                subscribe(BusTestEvent::class.java, handler)
                publish(BusTestEvent("test"))
            }

            handler.message shouldBe "foo"
        }

        it("does not publish events to other handlers") {
            val handler = BusTestEventHandler()
            RxEventBus().apply {
                subscribe(BusTestEvent::class.java, handler)
                publish(TestEvent())
            }

            handler.message shouldBe ""
        }
    }
})

class BusTestEvent(val value: String) : Event

class BusTestEventHandler() : EventHandler<BusTestEvent> {
    var message = ""
    override fun handle(event: BusTestEvent) {
        this.message += "foo"
    }
}
