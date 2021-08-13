package racoony.software.klubi.ports.store

import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import racoony.software.klubi.domain.member.Member

interface MemberRepository {
    fun findAll(): Multi<Member>
    fun save(member: Member): Uni<Void>
}

