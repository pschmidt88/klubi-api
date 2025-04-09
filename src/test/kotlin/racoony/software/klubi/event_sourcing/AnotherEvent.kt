package racoony.software.klubi.event_sourcing

data class AnotherEvent(
    val otherValue: String,
    val version: Long? = null
) : Event(version) {
    override fun copyWithVersion(version: Long): Event = copy(version = version)
}