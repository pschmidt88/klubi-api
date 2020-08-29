package racoony.software.klubi.adapter.mongodb

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import racoony.software.klubi.event_sourcing.Event
import java.util.UUID

data class MongoEvent(
    val aggregateId: UUID,
    val event: Event
) {
    @BsonId
    val id: ObjectId = ObjectId.get()
}
