package racoony.software.klubi.event_sourcing

data class AnotherEvent(
    val otherValue: String,
    override val version: Long? = null,
) : Event() {
    override fun copyWithVersion(version: Long): Event = copy(version = version)
}
