package racoony.software.klubi.adapter.json

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toKotlinInstant
import java.time.Instant as JavaTimeInstant

// This file contains jackson (de)serializers kotlin Instant data type (from kotlinx.datetime).
// It converts the kotlinx.datetime.Instant to java.time.Instant before serializing it to JSON.

class KotlinxInstantSerializer : JsonSerializer<Instant>() {
    override fun serialize(value: Instant, gen: JsonGenerator, serializers: SerializerProvider) {
        serializers.defaultSerializeValue(value.toJavaInstant(), gen)
    }
}

class KotlinxInstantDeserializer : JsonDeserializer<Instant>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Instant =
        ctxt.readValue(p, JavaTimeInstant::class.java).toKotlinInstant()
}
