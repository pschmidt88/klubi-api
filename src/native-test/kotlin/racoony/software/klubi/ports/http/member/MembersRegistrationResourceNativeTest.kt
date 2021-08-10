package racoony.software.klubi.ports.http.member

import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusIntegrationTest
import racoony.software.klubi.event_sourcing.storage.MongoDBTestResource

@QuarkusIntegrationTest
@QuarkusTestResource(MongoDBTestResource::class)
class MembersRegistrationResourceNativeTest : MembersRegistrationResourceTest()