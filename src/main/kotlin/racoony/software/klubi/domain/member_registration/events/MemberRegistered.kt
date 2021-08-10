package racoony.software.klubi.domain.member_registration.events

import racoony.software.klubi.domain.member_registration.AssignedDepartment
import racoony.software.klubi.domain.member_registration.MembershipFeePayment
import racoony.software.klubi.domain.member_registration.PersonalDetails
import racoony.software.klubi.event_sourcing.Event

class MemberRegistered(
    val personalDetails: PersonalDetails,
    val assignedDepartment: AssignedDepartment,
    val membershipFeePayment: MembershipFeePayment
) : Event
