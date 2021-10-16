package racoony.software.klubi.ports.http.member.list

import io.smallrye.mutiny.Uni
import org.jboss.logging.Logger
import racoony.software.klubi.domain.member.ReactiveMemberRepository
import racoony.software.klubi.ports.http.member.list.responses.MemberListResponse
import java.util.function.Consumer
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.core.Response

@Path("api/members")
class MemberListResource(
    private val memberRepository: ReactiveMemberRepository
) {

    @Inject
    private lateinit var logger: Logger

    @GET
    fun fetchMemberList(): Uni<Response> {
        return this.memberRepository.fetchAll()
            .collect()
            .asList()
            .onFailure().invoke(Consumer { logger.error(it) })
            .onItem().transform {
                Response.ok(MemberListResponse(it)).build()
            }
    }

}