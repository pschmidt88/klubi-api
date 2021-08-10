package racoony.software.klubi.domain.member_registration

import racoony.software.klubi.event_sourcing.Aggregate
import java.util.UUID

class MemberRegistration(
    id: UUID = UUID.randomUUID()
) : Aggregate(id) {

    fun registerNewMember(
        personalDetails: PersonalDetails,
        assignedDepartment: AssignedDepartment,
        membershipFeePayment: MembershipFeePayment
    ) {
        raise(MemberRegistered(personalDetails, assignedDepartment, membershipFeePayment))
    }
}
