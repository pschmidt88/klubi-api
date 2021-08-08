package racoony.software.klubi.event_sourcing.storage

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager
import org.testcontainers.containers.MongoDBContainer

class MongoDBTestResource : QuarkusTestResourceLifecycleManager {

    private val mongoDBContainer = MongoDBContainer("mongo")

    override fun start(): MutableMap<String, String> {
        mongoDBContainer.start()
        return mutableMapOf(
            "quarkus.mongodb.connection-string" to "mongodb://${mongoDBContainer.host}:${mongoDBContainer.firstMappedPort}/?uuidRepresentation=STANDARD"
        )
    }

    override fun stop() {
        mongoDBContainer.stop()
    }
}