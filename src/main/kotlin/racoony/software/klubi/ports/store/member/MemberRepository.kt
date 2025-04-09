package racoony.software.klubi.ports.store.member

import kotlinx.coroutines.flow.Flow
import racoony.software.klubi.domain.member.Member

interface MemberRepository {
    suspend fun findAll(): Iterable<Member>
    suspend fun save(member: Member): Result<Unit>
}

