package racoony.software.klubi.adapter.mongodb.codecs

import jakarta.enterprise.context.ApplicationScoped
import org.bson.codecs.Codec
import org.bson.codecs.configuration.CodecProvider
import org.bson.codecs.configuration.CodecRegistry


@ApplicationScoped
class KotlinxCodecProvider : CodecProvider {

    override fun <T : Any> get(clazz: Class<T>, codecRegistry: CodecRegistry): Codec<T>? {

//        if (clazz == BaseEventStore.EventDescriptor::class.java) {
//            return KotlinSerializerCodec.create<BaseEventStore.EventDescriptor>() as Codec<T>
//        }
//
//        if (clazz == Event::class.java) {
//            return KotlinSerializerCodec.create<Event>() as Codec<T>
//        }

        return null
    }

}

//@ApplicationScoped
//class Customizer : MongoClientCustomizer {
//    override fun customize(builder: MongoClientSettings.Builder?): MongoClientSettings.Builder {
//////        builder.codecRegistry()
////    }
//
//}