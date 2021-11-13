package racoony.software.klubi.ports.bus

import racoony.software.klubi.event_sourcing.Event

class BusTestEvent(val value: String) : Event()