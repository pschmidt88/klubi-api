package racoony.software.klubi.domain.department

import racoony.software.klubi.domain.DomainEvent

class DepartmentCreated(
    val id: DepartmentId,
    val name: DepartmentName
) : DomainEvent {
    override fun getAggregateId() = id

}
