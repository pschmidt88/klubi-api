package racoony.software.klubi.ports.http.member.registration

import io.smallrye.mutiny.Uni
import org.jboss.logging.Logger
import racoony.software.klubi.domain.member_registration.MemberRegistration
import racoony.software.klubi.domain.member_registration.MembershipFeePayment
import racoony.software.klubi.domain.member_registration.PaymentMethod
import racoony.software.klubi.domain.member_registration.PersonalDetails
import racoony.software.klubi.event_sourcing.AggregateRepository
import racoony.software.klubi.ports.http.member.registration.requests.MemberRegistrationRequest
import java.net.URI
import java.util.function.Consumer
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

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
    fun createMember(request: MemberRegistrationRequest): Uni<Response> {

        val personalDetails = request.personalDetails
        val assignedDepartment = request.assignedDepartment
        val membershipFeePayment = request.membershipFeePayment

        val memberRegistration = MemberRegistration().apply {
            registerNewMember(personalDetails, assignedDepartment, membershipFeePayment)
        }

        return repository.save(memberRegistration)
            .onItem().transform {
                Response.created(URI.create("/api/members/${memberRegistration.id}")).build()
            }
            .onFailure().invoke(Consumer { logger.error("Failed to save member registration aggregate: $it") })
    }

}
