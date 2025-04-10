package racoony.software.klubi.ports.bus

import racoony.software.klubi.event_sourcing.Event

data class BusTestEvent(
    val value: String,
    override val version: Long? = null,
) : Event() {
    override fun copyWithVersion(version: Long): Event = copy(version = version)
}
