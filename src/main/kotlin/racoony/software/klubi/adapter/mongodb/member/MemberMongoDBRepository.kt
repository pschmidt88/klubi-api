package racoony.software.klubi.adapter.mongodb.member

import io.quarkus.mongodb.reactive.ReactiveMongoClient
import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import racoony.software.klubi.domain.member.Member
import racoony.software.klubi.ports.store.member.MemberRepository

@ApplicationScoped
class MemberMongoDBRepository(
    private val client: ReactiveMongoClient
) : MemberRepository {

    private val collection = this.client
        .getDatabase("klubi")
        .getCollection("members", Member::class.java)

    override fun findAll(): Multi<Member> {
        return this.collection.find()
    }

    override fun save(member: Member): Uni<Void> {
        return this.collection.insertOne(member)
            .onItem().ignore().andContinueWithNull()
    }

}