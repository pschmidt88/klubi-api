package racoony.software.klubi.domain.member_registration.events

import racoony.software.klubi.event_sourcing.Event
import racoony.software.klubi.resource.requests.AssignedDepartment

class AssignedToDepartment(
    val assignedDepartment: AssignedDepartment
) : Event()
