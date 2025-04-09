package racoony.software.klubi.event_sourcing

data class TestEvent(
    val someValue: String,
    val version: Long? = null,
) : Event(version) {
    override fun copyWithVersion(version: Long): Event = copy(version = version)
}