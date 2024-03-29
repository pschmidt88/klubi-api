package racoony.software.klubi.ports.http.member.details

import io.smallrye.mutiny.Uni
import racoony.software.klubi.domain.member.MemberProjection
import racoony.software.klubi.ports.store.EventStore
import java.util.*
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/api/members")
class MembersResource(
    private val eventStore: EventStore,
) {
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun find(id: UUID): Uni<Response> {
        return eventStore.loadEvents(id)
            .collect().asList()
            .onItem().transform {
                MemberProjection().apply {
                    restoreFromHistory(it)
                }
            }
            .onItem().transform { Response.ok(it.toMember()).build() }

    }
}