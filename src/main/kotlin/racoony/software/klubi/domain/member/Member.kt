package racoony.software.klubi.domain.member

import io.quarkus.mongodb.panache.common.MongoEntity
import org.bson.types.ObjectId

@MongoEntity(collection = "members")
data class Member(
    var id: ObjectId? = null,
    var number: String,
    var name: String,
    var address: String,
    var contact: String?,
)
