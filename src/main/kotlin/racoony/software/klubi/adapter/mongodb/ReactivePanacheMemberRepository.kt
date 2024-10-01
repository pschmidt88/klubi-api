package racoony.software.klubi.adapter.mongodb

import io.quarkus.mongodb.panache.kotlin.reactive.ReactivePanacheMongoRepository
import io.smallrye.mutiny.Multi
import jakarta.enterprise.context.ApplicationScoped
import racoony.software.klubi.domain.member.Member
import racoony.software.klubi.domain.member.ReactiveMemberRepository

@ApplicationScoped
class ReactivePanacheMemberRepository : ReactiveMemberRepository, ReactivePanacheMongoRepository<Member> {
    override fun fetchAll(): Multi<Member> {
        return this.findAll().stream()
    }
}
