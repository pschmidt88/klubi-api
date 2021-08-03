package racoony.software.klubi.ports.http.member

import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.CoreMatchers.startsWith
import org.junit.jupiter.api.Test
import racoony.software.klubi.ports.MongoDBTestResource
import javax.ws.rs.core.MediaType

@QuarkusTest
@QuarkusTestResource(MongoDBTestResource::class)
class MembersRegistrationResourceTest {

    @Test
    fun `request with valid body creates user and returns 201 with location header`() {
        Given {
            body("""
                {
                    "firstName": "Paul",
                    "lastName": "Schmidt",
                    "streetAddress": "Aschrottstraße",
                    "streetNumber": "4",
                    "postalCode": "34119",
                    "city": "Kassel",
                    "birthday": "1988-06-16",
                    "phone": "",
                    "email": "rookian@gmail.com",
                    "department": "football",
                    "entryDate": "2020-06-01",
                    "memberStatus": "active",
                    "paymentMethod": "bank_transfer"
                }
            """.trimIndent())
            contentType(MediaType.APPLICATION_JSON)
        } When {
            post("/api/members/registration")
        } Then {
            statusCode(201)
            header("Location", startsWith("/api/members/"))
        }
    }

}