package racoony.software.klubi.adapter.mongodb.codecs

import org.bson.BsonReader
import org.bson.BsonWriter
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext
import kotlin.uuid.Uuid

class UuidCodec : Codec<Uuid>  {

    override fun encode(writer: BsonWriter, value: Uuid, encoderContext: EncoderContext) {
        writer.writeString(value.toString())
    }

    override fun getEncoderClass(): Class<Uuid> = Uuid::class.java

    override fun decode(reader: BsonReader, decoderContext: DecoderContext): Uuid {
        return Uuid.parse(reader.readString())
    }
}
