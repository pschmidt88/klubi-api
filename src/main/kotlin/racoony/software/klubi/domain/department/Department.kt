package racoony.software.klubi.domain.department

import racoony.software.klubi.domain.DomainEvent
import racoony.software.klubi.event_sourcing.Aggregate
import java.util.UUID

val emptyDepartmentName = DepartmentName("")

class Department : Aggregate<DepartmentId>(DepartmentId(UUID.randomUUID())) {
    var name: DepartmentName = emptyDepartmentName
        private set

    companion object {
        fun create(name: DepartmentName): Department {
            return Department().apply {
                raise(DepartmentCreated(DepartmentId(UUID.randomUUID()), name))
            }
        }
    }

    override fun applyDomainEvents(events: List<DomainEvent>) {
        events.forEach {
            when(it) {
                is DepartmentCreated -> apply(it)
            }
        }
    }

    private fun apply(event: DepartmentCreated) {
        this.id = event.id
        this.name = event.name
    }
}

@JvmInline
value class DepartmentName(private val name: String)
