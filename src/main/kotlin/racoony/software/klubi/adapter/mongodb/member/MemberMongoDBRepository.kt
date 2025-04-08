package racoony.software.klubi.adapter.mongodb.member

import io.quarkus.mongodb.reactive.ReactiveMongoClient
import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.coroutines.asFlow
import io.smallrye.mutiny.coroutines.awaitSuspending
import jakarta.enterprise.context.ApplicationScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import racoony.software.klubi.domain.member.Member
import racoony.software.klubi.ports.store.member.MemberRepository

@ApplicationScoped
class MemberMongoDBRepository(
    private val client: ReactiveMongoClient
) : MemberRepository {

    private val collection = this.client
        .getDatabase("klubi")
        .getCollection("members", Member::class.java)

    override suspend fun findAll(): Flow<Member> {
        return this.collection.find().asFlow()
    }

    override suspend fun save(member: Member): Result<Unit> = runCatching {
        this.collection.insertOne(member).awaitSuspending()
    }
}