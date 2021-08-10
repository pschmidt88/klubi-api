package racoony.software.klubi.domain.member_registration

import racoony.software.klubi.event_sourcing.Event

class MemberRegistered(
    val personalDetails: PersonalDetails,
    val assignedDepartment: AssignedDepartment,
    val membershipFeePayment: MembershipFeePayment
) : Event
