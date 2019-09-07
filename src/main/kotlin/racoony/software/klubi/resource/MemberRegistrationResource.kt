package racoony.software.klubi.resource

import racoony.software.klubi.domain.member_registration.BankDetails
import racoony.software.klubi.domain.member_registration.MemberRegistration
import racoony.software.klubi.domain.member_registration.PaymentMethod
import racoony.software.klubi.domain.member_registration.PersonalDetails
import racoony.software.klubi.event_sourcing.AggregateRepository
import racoony.software.klubi.resource.requests.AssignedDepartment
import racoony.software.klubi.resource.requests.MemberRegistrationRequest
import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.core.MediaType

@Path("/api/member/registration")
class MemberRegistrationResource(
    private val repository: AggregateRepository<MemberRegistration>
) {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    fun createMember(@Valid request: MemberRegistrationRequest) {
        val memberRegistration = MemberRegistration().apply {
            addPersonalDetails(personalDetailsFromRequest(request))
            assignToDepartment(assignedDepartmentFromRequest(request))
            setPaymentMethod(paymentMethodFromRequest(request), bankDetailsFromRequest(request))
        }

        repository.save(memberRegistration)
    }

    private fun paymentMethodFromRequest(request: MemberRegistrationRequest): PaymentMethod {
        return request.paymentMethod()
    }

    private fun bankDetailsFromRequest(request: MemberRegistrationRequest): BankDetails? {
        return request.bankDetails()
    }

    private fun assignedDepartmentFromRequest(request: MemberRegistrationRequest): AssignedDepartment {
        return request.assignedDepartment()
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
