package racoony.software.klubi.resource.member.registration

import racoony.software.klubi.domain.member_registration.MemberRegistration
import racoony.software.klubi.domain.member_registration.PersonalDetails
import racoony.software.klubi.event_sourcing.AggregateRepository
import racoony.software.klubi.resource.member.registration.requests.MemberRegistrationRequest
import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.core.MediaType

@Path("/api/members/registration")
class MembersRegistrationResource(
    private val repository: AggregateRepository<MemberRegistration>
) {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    fun createMember(@Valid request: MemberRegistrationRequest) {
        val memberRegistration = MemberRegistration().apply {
            addPersonalDetails(personalDetailsFromRequest(request))
            assignToDepartment(request.assignedDepartment())
            selectPaymentMethod(request.paymentMethod(), request.bankDetails())
        }

        repository.save(memberRegistration)
    }

    private fun personalDetailsFromRequest(request: MemberRegistrationRequest): PersonalDetails {
        return PersonalDetails(
            request.name(),
            request.address(),
            request.birthday(),
            request.contact()
        )
    }
}
