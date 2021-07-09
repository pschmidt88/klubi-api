package racoony.software.klubi.resource.member.registration

import io.smallrye.mutiny.Uni
import racoony.software.klubi.domain.member_registration.MemberRegistration
import racoony.software.klubi.domain.member_registration.PersonalDetails
import racoony.software.klubi.event_sourcing.AggregateRepository
import racoony.software.klubi.resource.member.registration.requests.MemberRegistrationRequest
import java.net.URI
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/api/members/registration")
class MembersRegistrationResource(
    @Inject private val repository: AggregateRepository<MemberRegistration>
) {

    @Path("/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun createMember(request: MemberRegistrationRequest): Uni<Response> {
        val memberRegistration = MemberRegistration().apply {
            addPersonalDetails(personalDetailsFromRequest(request))
            assignToDepartment(request.assignedDepartment())
            selectPaymentMethod(request.paymentMethod(), request.bankDetails())
        }

        repository.save(memberRegistration)

        return Uni.createFrom().item(memberRegistration)
            .onItem().transform { Response.created(URI.create("/api/members/${memberRegistration.id}")) }
            .onItem().transform { it.build() }
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
