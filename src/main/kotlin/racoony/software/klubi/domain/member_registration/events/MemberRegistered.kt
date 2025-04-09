package racoony.software.klubi.domain.member_registration.events

import racoony.software.klubi.domain.member_registration.AssignedDepartment
import racoony.software.klubi.domain.member_registration.MembershipFeePayment
import racoony.software.klubi.domain.member_registration.PersonalDetails
import racoony.software.klubi.event_sourcing.Event

data class MemberRegistered(
    val personalDetails: PersonalDetails,
    val assignedDepartment: AssignedDepartment,
    val membershipFeePayment: MembershipFeePayment,
    val version: Long? = null
) : Event(version) {
    override fun copyWithVersion(version: Long): MemberRegistered = copy(version = version)
}
