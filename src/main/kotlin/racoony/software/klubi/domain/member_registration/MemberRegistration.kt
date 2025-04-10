package racoony.software.klubi.domain.member_registration

import racoony.software.klubi.domain.member_registration.events.MemberRegistered
import racoony.software.klubi.event_sourcing.AggregateRoot
import racoony.software.klubi.event_sourcing.Event
import java.util.UUID

class MemberRegistration(
    id: UUID = UUID.randomUUID()
) : AggregateRoot(id) {

    fun registerNewMember(
        personalDetails: PersonalDetails,
        assignedDepartment: AssignedDepartment,
        membershipFeePayment: MembershipFeePayment
    ) {
        raise(MemberRegistered(personalDetails, assignedDepartment, membershipFeePayment))
    }

    override fun applyEvent(event: Event): AggregateRoot {
        return this
    }
}
