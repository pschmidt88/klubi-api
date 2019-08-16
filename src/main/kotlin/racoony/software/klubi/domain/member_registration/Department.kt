package racoony.software.klubi.domain.member_registration

import org.joda.time.LocalDate

class DepartmentAssignment(
    val department: Department,
    val memberStatus: MemberStatus,
    val entryDate: LocalDate
)

class Department(
    val name: String
)

enum class MemberStatus {
    ACTIVE,
    PASSIVE
}