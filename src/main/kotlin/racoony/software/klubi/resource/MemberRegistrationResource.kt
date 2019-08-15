package racoony.software.klubi.resource

import racoony.software.klubi.resource.requests.AddPersonalDetailsRequest
import javax.ws.rs.POST
import javax.ws.rs.Path

@Path("/api/member/registration")
class MemberRegistrationResource {

    @POST
    @Path("/personal-details")
    fun addPersonalDetails(request: AddPersonalDetailsRequest) {
    }
}