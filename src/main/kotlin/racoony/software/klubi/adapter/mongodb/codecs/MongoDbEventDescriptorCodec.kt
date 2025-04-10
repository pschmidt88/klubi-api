package racoony.software.klubi.adapter.mongodb.codecs

import org.bson.BsonReader
import org.bson.BsonWriter
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext
import racoony.software.klubi.adapter.mongodb.events.MongoDbEventDescriptor




class MongoDbEventDescriptorCodec : Codec<MongoDbEventDescriptor> {
    override fun encode(writer: BsonWriter?, value: MongoDbEventDescriptor?, encoderContext: EncoderContext?) {

    }

    override fun getEncoderClass(): Class<MongoDbEventDescriptor> {
        TODO("Not yet implemented")
    }

    override fun decode(reader: BsonReader?, decoderContext: DecoderContext?): MongoDbEventDescriptor {
        TODO("Not yet implemented")
    }
}
