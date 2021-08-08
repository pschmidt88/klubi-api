package racoony.software.klubi.adapter.mongodb.codecs

import com.fasterxml.jackson.databind.ObjectMapper
import org.bson.codecs.Codec
import org.bson.codecs.configuration.CodecProvider
import org.bson.codecs.configuration.CodecRegistry
import racoony.software.klubi.event_sourcing.Event
import javax.inject.Inject

@Suppress("unused") // will be auto registered by quarkus
class EventCodecProvider : CodecProvider {

    override fun <T> get(clazz: Class<T>, registry: CodecRegistry): Codec<T>? {
        if (clazz == Event::class.java) {
            return EventCodec() as Codec<T>
        }

        return null
    }
}