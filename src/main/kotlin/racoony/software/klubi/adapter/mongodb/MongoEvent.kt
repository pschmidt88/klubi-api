package racoony.software.klubi.adapter.mongodb

import io.quarkus.runtime.annotations.RegisterForReflection
import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId
import racoony.software.klubi.event_sourcing.Event
import java.util.UUID

@RegisterForReflection
data class MongoEvent @BsonCreator constructor(
    @BsonId val id: ObjectId = ObjectId.get(),
    @BsonProperty("aggregateId") val aggregateId: UUID,
    @BsonProperty("event") val event: Event
) {
}
