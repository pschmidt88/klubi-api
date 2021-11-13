package racoony.software.klubi.domain.member

import io.smallrye.mutiny.Multi

interface ReactiveMemberRepository {
    fun fetchAll(): Multi<Member>
}
