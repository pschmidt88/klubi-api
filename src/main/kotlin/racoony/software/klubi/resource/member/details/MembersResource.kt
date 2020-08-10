package racoony.software.klubi.resource.member.details

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Produces
import racoony.software.klubi.domain.member.MemberDetailsProjection
import racoony.software.klubi.ports.store.EventStore
import java.util.UUID

@Controller("/api/members")
class MembersResource(
    private val eventStore: EventStore
) {
    @Get("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun find(@PathVariable("id") id: UUID): HttpResponse<*> {
        val memberDetailsProjection = MemberDetailsProjection().apply {
            restoreFromHistory(eventStore.loadEvents(id))
        }

        return HttpResponse.ok(memberDetailsProjection.toJson())
    }
}