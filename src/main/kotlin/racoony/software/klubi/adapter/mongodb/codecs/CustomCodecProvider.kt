package racoony.software.klubi.adapter.mongodb.codecs

import io.quarkus.logging.Log
import org.bson.codecs.Codec
import org.bson.codecs.configuration.CodecProvider
import org.bson.codecs.configuration.CodecRegistry
import racoony.software.klubi.event_sourcing.Event
import kotlin.uuid.Uuid

@Suppress("unused") // This class is used by Quarkus to register the codecs
class CustomCodecProvider : CodecProvider {
    override fun <T : Any?> get(clazz: Class<T>, registry: CodecRegistry): Codec<T>? {
        when (clazz) {
            Uuid::class.java -> return UuidCodec() as Codec<T>
            Event::class.java -> return EventCodec(registry) as Codec<T>
            else -> Log.debug("No codec found for class: ${clazz.name}")
        }

        return null
    }
}

