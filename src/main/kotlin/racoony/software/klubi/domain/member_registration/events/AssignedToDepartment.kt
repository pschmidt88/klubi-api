package racoony.software.klubi.domain.member_registration.events

import org.joda.time.LocalDate
import racoony.software.klubi.domain.member_registration.Department
import racoony.software.klubi.domain.member_registration.MemberStatus
import racoony.software.klubi.event_sourcing.Event

class AssignedToDepartment(
    val department: Department,
    val memberStatus: MemberStatus,
    val entryDate: LocalDate
) : Event()
