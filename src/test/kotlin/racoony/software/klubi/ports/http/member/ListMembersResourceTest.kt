package racoony.software.klubi.ports.http.member

import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.Test
import racoony.software.klubi.event_sourcing.storage.MongoDBTestResource

@QuarkusTest
@QuarkusTestResource(MongoDBTestResource::class)
class ListMembersResourceTest {

    @Test
    fun `Lists multiple club members`() {
    }
}