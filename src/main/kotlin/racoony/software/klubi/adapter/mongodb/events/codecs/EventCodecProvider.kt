package racoony.software.klubi.adapter.mongodb.events.codecs

import org.bson.codecs.Codec
import org.bson.codecs.configuration.CodecProvider
import org.bson.codecs.configuration.CodecRegistry
import racoony.software.klubi.event_sourcing.Event

@Suppress("unused") // will be auto registered by quarkus
class EventCodecProvider : CodecProvider {

    override fun <T> get(clazz: Class<T>, registry: CodecRegistry): Codec<T>? {
        if (clazz == Event::class.java) {
            return EventCodec() as Codec<T>
        }

        return null
    }
}