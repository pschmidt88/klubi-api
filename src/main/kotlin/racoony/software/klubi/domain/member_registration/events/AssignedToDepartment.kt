package racoony.software.klubi.domain.member_registration.events

import racoony.software.klubi.domain.member_registration.AssignedDepartment
import racoony.software.klubi.event_sourcing.Event

class AssignedToDepartment(
    val assignedDepartment: AssignedDepartment
) : Event
