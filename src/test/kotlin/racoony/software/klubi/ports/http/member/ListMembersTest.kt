package racoony.software.klubi.ports.http.member

import com.mongodb.client.MongoClient
import io.kotest.matchers.collections.shouldHaveSize
import io.quarkus.test.junit.QuarkusTest
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import racoony.software.klubi.domain.member.Member
import racoony.software.klubi.event_sourcing.Stories
import racoony.software.klubi.ports.http.member.list.responses.MemberListResponse
import racoony.software.klubi.ports.store.EventStore
import java.util.UUID
import javax.inject.Inject

@QuarkusTest
class ListMembersTest {

    @Inject
    lateinit var mongoClient: MongoClient

    @AfterEach
    internal fun tearDown() {
        mongoClient.getDatabase("klubi").getCollection("members").drop()
    }

    @Test
    fun `given multiple registered members it fetches a list of members`() {
        // Given multiple registered members
        val userCollection = mongoClient.getDatabase("klubi")
            .getCollection("members", Member::class.java)

        repeat(5) {
            userCollection.insertOne(createRandomMember())
        }

        val memberListResponse = When {
            get("/api/members")
        } Then {
            statusCode(200)
        } Extract {
            `as`(MemberListResponse::class.java)
        }

        memberListResponse.members shouldHaveSize 5
    }

    private fun createRandomMember(): Member {
        return Member(
            number = "24",
            firstName = "Luka Jovic",
            streetAddress = "Real Madrid Straße",
            emailAddress = "lukajovic@eintracht.de"
        )
    }
}