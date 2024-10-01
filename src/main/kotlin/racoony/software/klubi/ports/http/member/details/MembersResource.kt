package racoony.software.klubi.ports.http.member.details

import io.smallrye.mutiny.Uni
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import kotlinx.coroutines.flow.toList
import racoony.software.klubi.domain.member.MemberProjection
import racoony.software.klubi.ports.store.EventStore
import java.util.*

@Path("/api/members")
class MembersResource(
    private val eventStore: EventStore,
) {
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun find(id: UUID): Response {
        return eventStore.loadEvents(id).toList()
            .let { MemberProjection().apply { restoreFromHistory(it) } }
            .let { Response.ok(it.toMember()).build() }
    }
}