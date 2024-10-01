package racoony.software.klubi.ports.http.member.registration

import io.smallrye.mutiny.Uni
import jakarta.inject.Inject
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.jboss.logging.Logger
import racoony.software.klubi.domain.member_registration.MemberRegistration
import racoony.software.klubi.event_sourcing.AggregateRepository
import racoony.software.klubi.ports.http.member.registration.requests.MemberRegistrationRequest
import java.net.URI
import java.util.function.Consumer

@Path("/api/members/registration")
class MembersRegistrationResource(
    private val repository: AggregateRepository<MemberRegistration>
) {
    @Inject
    lateinit var logger: Logger

    @Path("/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun createMember(request: MemberRegistrationRequest): Response {

        val personalDetails = request.personalDetails
        val assignedDepartment = request.assignedDepartment
        val membershipFeePayment = request.membershipFeePayment

        val memberRegistration = MemberRegistration().apply {
            registerNewMember(personalDetails, assignedDepartment, membershipFeePayment)
        }

        return repository.save(memberRegistration)
            .onFailure { logger.error("Failed to save member registration aggregate: $it") }
            .fold(
                { Response.created(URI.create("/api/members/${memberRegistration.id}")).build() },
                { Response.serverError().build() }
            )

    }

}
