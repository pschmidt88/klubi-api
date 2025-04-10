package racoony.software.klubi.event_sourcing

data class TestEvent(
    val someValue: String,
    override val version: Long? = null,
) : Event() {
    override fun copyWithVersion(version: Long): Event = copy(version = version)
}
