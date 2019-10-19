package racoony.software.klubi.resource.member.details

import racoony.software.klubi.domain.member.MemberDetailsProjection
import racoony.software.klubi.ports.store.EventStore
import java.util.UUID
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/api/v0/members")
class MembersResource(
    private val eventStore: EventStore
) {

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun find(@PathParam("id") id: UUID): Response {
        val memberDetailsProjection = MemberDetailsProjection().apply {
            restoreFromHistory(eventStore.loadEvents(id))
        }

        return Response.ok()
            .entity(memberDetailsProjection.toJson())
            .build()
    }
}