package racoony.software.klubi.domain.member_registration.event_handler

import io.quarkus.vertx.ConsumeEvent
import io.smallrye.mutiny.Uni
import org.jboss.logging.Logger
import racoony.software.klubi.domain.member.MemberProjection
import racoony.software.klubi.domain.member_registration.events.MemberRegistered
import racoony.software.klubi.ports.store.MemberRepository
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@Suppress("unused")
@ApplicationScoped
class BuildMemberProjectionAfterRegistration(
    private val memberRepository: MemberRepository
) {
    @Inject
    lateinit var logger: Logger

    @ConsumeEvent("MemberRegistered")
    fun createMemberProjection(event: MemberRegistered): Uni<Void> {
        logger.info("Handling MemberRegistered event")
        val member = MemberProjection().apply {
            restoreFromHistory(listOf(event))
        }.toMember()

        return memberRepository.save(member)
            .onFailure().invoke { cause -> logger.error("Failed to save member", cause) }
            .onItem().ignore().andContinueWithNull()
    }
}
