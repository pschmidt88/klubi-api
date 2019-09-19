package racoony.software.klubi.resource.member.details

import racoony.software.klubi.domain.member.MemberDetails
import java.util.UUID
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/api/members")
class MembersResource {

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun find(@PathParam("id") id: UUID): MemberDetails {
//        return MemberDetailsRepository().findById(id)
        return MemberDetails()
    }
}