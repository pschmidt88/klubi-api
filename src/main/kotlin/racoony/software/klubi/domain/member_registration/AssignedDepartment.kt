package racoony.software.klubi.domain.member_registration

import java.time.LocalDate

data class AssignedDepartment(
    val department: Department,
    val memberStatus: MemberStatus,
    val entryDate: LocalDate
)
