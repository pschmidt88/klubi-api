package racoony.software.klubi.adapter.mongodb.member

import io.quarkus.mongodb.reactive.ReactiveMongoClient
import io.smallrye.mutiny.coroutines.awaitSuspending
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

    override suspend fun findAll(): Iterable<Member> {
        return this.collection.find().collect().asList().awaitSuspending().asIterable()
    }

    override suspend fun save(member: Member): Result<Unit> = runCatching {
        this.collection.insertOne(member).awaitSuspending()
    }
}
