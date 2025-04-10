package racoony.software.klubi.ports.http.member.details

import arrow.core.getOrElse
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import kotlinx.coroutines.flow.toList
import org.jboss.resteasy.reactive.ResponseStatus
import racoony.software.klubi.domain.member.MemberProjection
import racoony.software.klubi.event_sourcing.EventStore
import java.util.*

@Path("/api/members")
class MembersResource(
    private val eventStore: EventStore,
) {
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun find(id: UUID): Response {
        return eventStore.findEventsByAggregateId(id)
            .map { MemberProjection().apply { restoreFromHistory(it) } }
            .map { Response.ok(it.toMember()).build() }
            .getOrElse { Response.status(404).build() }
    }
}