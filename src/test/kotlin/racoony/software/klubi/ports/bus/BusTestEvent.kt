package racoony.software.klubi.ports.bus

import racoony.software.klubi.event_sourcing.Event

data class BusTestEvent(
    val value: String,
    private val version: Long? = null
) : Event(version) {
    override fun copyWithVersion(version: Long): Event = copyWithVersion(version = version)
}