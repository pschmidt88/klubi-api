package racoony.software.klubi.application

import com.fasterxml.jackson.databind.ObjectMapper
import io.quarkus.runtime.Startup
import racoony.software.klubi.domain.member_registration.event_handler.BuildMemberProjectionAfterRegistration
import racoony.software.klubi.domain.member_registration.events.MemberRegistered
import racoony.software.klubi.ports.bus.EventBus
import racoony.software.klubi.ports.store.MemberRepository
import javax.enterprise.context.ApplicationScoped

@Startup
@ApplicationScoped
class EventRegistration(
    private val eventBus: EventBus,
    private val memberRepository: MemberRepository,
) {
    init {
        this.eventBus.subscribe(MemberRegistered::class.java, BuildMemberProjectionAfterRegistration(memberRepository))
    }
}
