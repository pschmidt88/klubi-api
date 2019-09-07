package racoony.software.klubi.resource.requests

import racoony.software.klubi.domain.member_registration.Department
import racoony.software.klubi.domain.member_registration.MemberStatus
import java.time.LocalDate

class AssignedDepartment(
    val department: Department,
    val memberStatus: MemberStatus,
    val entryDate: LocalDate
)