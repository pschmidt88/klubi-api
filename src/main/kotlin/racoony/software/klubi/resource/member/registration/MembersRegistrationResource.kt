package racoony.software.klubi.resource.member.registration

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Consumes
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import racoony.software.klubi.domain.member_registration.MemberRegistration
import racoony.software.klubi.domain.member_registration.PersonalDetails
import racoony.software.klubi.event_sourcing.AggregateRepository
import racoony.software.klubi.resource.member.registration.requests.MemberRegistrationRequest
import java.net.URI

@Controller("/api/members/registration")
class MembersRegistrationResource(
    private val repository: AggregateRepository<MemberRegistration>
) {

    @Post("/")
    @Consumes(MediaType.APPLICATION_JSON)
    fun createMember(@Body request: MemberRegistrationRequest): HttpResponse<*> {
        val memberRegistration = MemberRegistration().apply {
            addPersonalDetails(personalDetailsFromRequest(request))
            assignToDepartment(request.assignedDepartment())
            selectPaymentMethod(request.paymentMethod(), request.bankDetails())
        }

        repository.save(memberRegistration)

        return HttpResponse.created<Any>(URI.create("/api/members/${memberRegistration.id}"))
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
