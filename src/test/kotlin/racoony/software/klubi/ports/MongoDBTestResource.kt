package racoony.software.klubi.ports

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager
import org.testcontainers.containers.MongoDBContainer

class MongoDBTestResource : QuarkusTestResourceLifecycleManager {

    private val mongoDBContainer = MongoDBContainer("mongo")

    override fun start(): MutableMap<String, String> {
        mongoDBContainer.start()
        return mutableMapOf(
            "mongodb.connectionString" to "mongodb://${mongoDBContainer.host}:${mongoDBContainer.firstMappedPort}"
        )
    }

    override fun stop() {
        mongoDBContainer.stop()
    }
}