package racoony.software.klubi.domain.member_registration.event_handler

import jakarta.enterprise.context.ApplicationScoped
import kotlinx.coroutines.runBlocking
import org.jboss.logging.Logger
import racoony.software.klubi.domain.member.MemberProjection
import racoony.software.klubi.domain.member_registration.events.MemberRegistered
import racoony.software.klubi.ports.bus.EventHandler
import racoony.software.klubi.ports.store.member.MemberRepository

@ApplicationScoped
class BuildMemberProjectionAfterRegistration(
    private val memberRepository: MemberRepository
) : EventHandler<MemberRegistered> {

    companion object {
        @JvmStatic
        private val logger: Logger = Logger.getLogger(BuildMemberProjectionAfterRegistration::class.java)
    }

    override fun handle(event: MemberRegistered) {
        logger.info("Handling MemberRegistered event")
        val member = MemberProjection().apply {
            restoreFromHistory(listOf(event))
        }.toMember()

        runBlocking {
            runCatching { memberRepository.save(member) }
                .onFailure { cause -> logger.error("Failed to save member", cause) }
        }
    }
}
