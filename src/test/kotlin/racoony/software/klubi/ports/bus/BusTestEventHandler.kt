package racoony.software.klubi.ports.bus

class BusTestEventHandler : EventHandler<BusTestEvent> {
    var message = ""
    override fun handle(event: BusTestEvent) {
        this.message += "foo"
    }
}