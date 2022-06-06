package racoony.software.klubi.domain

interface DomainEvent {
    fun getAggregateId(): AggregateId
}
