package racoony.software.klubi.adapter.mongodb.codecs

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.addDeserializer
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.datetime.Instant
import org.bson.BsonReader
import org.bson.BsonWriter
import org.bson.Document
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext
import org.bson.codecs.configuration.CodecRegistry
import racoony.software.klubi.adapter.json.KotlinxInstantDeserializer
import racoony.software.klubi.adapter.json.KotlinxInstantSerializer
import racoony.software.klubi.event_sourcing.Event


class EventCodec(
    private val codecRegistry: CodecRegistry,
) : Codec<Event> {

    private val objectMapper: ObjectMapper by lazy { jacksonObjectMapper().apply {
        val kotlinInstantModule = SimpleModule().apply {
            addSerializer(Instant::class.java, KotlinxInstantSerializer())
            addDeserializer(Instant::class.java, KotlinxInstantDeserializer())
        }

        registerModules(Jdk8Module(), JavaTimeModule(), kotlinInstantModule)
        addMixIn(Event::class.java, JacksonEventMixin::class.java)
    } }

    private val documentCodec: Codec<Document> by lazy { codecRegistry[Document::class.java] }

    override fun encode(writer: BsonWriter, value: Event, encoderContext: EncoderContext) =
        Document.parse(objectMapper.writeValueAsString(value)).let {
            document -> documentCodec.encode(writer, document, encoderContext)
        }

    override fun getEncoderClass(): Class<Event> = Event::class.java

    override fun decode(reader: BsonReader, decoderContext: DecoderContext): Event =
        documentCodec.decode(reader, decoderContext).let { document -> objectMapper.readValue(document.toJson()) }
}

@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS, include = JsonTypeInfo.As.PROPERTY, property = "_t")
@JsonIgnoreProperties(ignoreUnknown = true)
interface JacksonEventMixin
