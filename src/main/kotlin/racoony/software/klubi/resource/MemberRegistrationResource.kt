package racoony.software.klubi.resource

import racoony.software.klubi.domain.member_registration.BankDetails
import racoony.software.klubi.domain.member_registration.DepartmentAssignment
import racoony.software.klubi.domain.member_registration.MemberRegistration
import racoony.software.klubi.domain.member_registration.PaymentMethod
import racoony.software.klubi.domain.member_registration.PersonalDetails
import racoony.software.klubi.event_sourcing.AggregateRepository
import racoony.software.klubi.resource.requests.MemberRegistrationRequest
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
    fun createMember(request: MemberRegistrationRequest) {
        val memberRegistration = MemberRegistration().apply {
            addPersonalDetails(personalDetailsFromRequest(request))
            departmentsFromRequest(request).forEach {
                assignToDepartment(it)
            }
            setPaymentMethod(paymentMethodFromRequest(request), bankDetailsFromRequest(request))
        }

        repository.save(memberRegistration)
    }

    private fun paymentMethodFromRequest(request: MemberRegistrationRequest): PaymentMethod {
        return request.paymentMethod
    }

    private fun bankDetailsFromRequest(request: MemberRegistrationRequest): BankDetails? {
        return request.bankDetails
    }

    private fun departmentsFromRequest(request: MemberRegistrationRequest): List<DepartmentAssignment> {
        return request.departments
    }

    private fun personalDetailsFromRequest(request: MemberRegistrationRequest): PersonalDetails {
        return PersonalDetails(
                request.firstName,
                request.lastName,
                request.address,
                request.birthday,
                request.phone,
                request.email
        )
    }
}