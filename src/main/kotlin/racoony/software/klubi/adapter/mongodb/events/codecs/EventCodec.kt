package racoony.software.klubi.adapter.mongodb.events.codecs

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.mongodb.MongoClientSettings
import org.bson.BsonReader
import org.bson.BsonWriter
import org.bson.Document
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext
import racoony.software.klubi.event_sourcing.Event

class EventCodec(
    private val documentCodec: Codec<Document> = MongoClientSettings.getDefaultCodecRegistry().get(Document::class.java),
) : Codec<Event> {

    private val objectMapper: ObjectMapper = jacksonObjectMapper().apply {
        registerModule(Jdk8Module())
        registerModule(JavaTimeModule())
    }

    override fun encode(writer: BsonWriter?, value: Event?, encoderContext: EncoderContext?) {
        val document = Document.parse(objectMapper.writeValueAsString(value))
        documentCodec.encode(writer, document, encoderContext)
    }

    override fun getEncoderClass(): Class<Event> {
        return Event::class.java
    }

    override fun decode(reader: BsonReader?, decoderContext: DecoderContext?): Event {
        val document = documentCodec.decode(reader, decoderContext)
        return objectMapper.readValue(document.toJson())
    }
}

