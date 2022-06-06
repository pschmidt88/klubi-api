package racoony.software.klubi.domain.department

import racoony.software.klubi.domain.AggregateId

data class DepartmentId(val id: Any) : AggregateId {
    override fun aggregateIdToString() = id.toString()
}
