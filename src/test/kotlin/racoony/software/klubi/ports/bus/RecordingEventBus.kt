package racoony.software.klubi.ports.bus

import io.vertx.core.Future
import io.vertx.core.Handler
import io.vertx.core.eventbus.DeliveryContext
import io.vertx.core.eventbus.DeliveryOptions
import io.vertx.core.eventbus.EventBus
import io.vertx.core.eventbus.Message
import io.vertx.core.eventbus.MessageCodec
import io.vertx.core.eventbus.MessageConsumer
import io.vertx.core.eventbus.MessageProducer
import racoony.software.klubi.event_sourcing.Event

class RecordingEventBus : EventBus {
    private val publishedEvents: MutableMap<String, MutableList<Any?>> = mutableMapOf()

    override fun send(address: String?, message: Any?): EventBus {
        TODO("Not yet implemented")
    }

    override fun send(address: String?, message: Any?, options: DeliveryOptions?): EventBus {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> request(address: String?, message: Any?, options: DeliveryOptions?): Future<Message<T>> {
        TODO("Not yet implemented")
    }

    override fun publish(address: String?, message: Any?): EventBus {
        this.publish(address, message, DeliveryOptions())
        return this
    }

    override fun publish(address: String?, message: Any?, options: DeliveryOptions?): EventBus {
        this.publishedEvents.getOrPut(address!!, { mutableListOf() }).add(message)
        return this
    }

    override fun <T : Any?> consumer(address: String?): MessageConsumer<T> {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> consumer(address: String?, handler: Handler<Message<T>>?): MessageConsumer<T> {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> localConsumer(address: String?): MessageConsumer<T> {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> localConsumer(address: String?, handler: Handler<Message<T>>?): MessageConsumer<T> {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> sender(address: String?): MessageProducer<T> {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> sender(address: String?, options: DeliveryOptions?): MessageProducer<T> {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> publisher(address: String?): MessageProducer<T> {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> publisher(address: String?, options: DeliveryOptions?): MessageProducer<T> {
        TODO("Not yet implemented")
    }

    override fun registerCodec(codec: MessageCodec<*, *>?): EventBus {
        TODO("Not yet implemented")
    }

    override fun unregisterCodec(name: String?): EventBus {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> registerDefaultCodec(clazz: Class<T>?, codec: MessageCodec<T, *>?): EventBus {
        TODO("Not yet implemented")
    }

    override fun unregisterDefaultCodec(clazz: Class<*>?): EventBus {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> addOutboundInterceptor(interceptor: Handler<DeliveryContext<T>>?): EventBus {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> removeOutboundInterceptor(interceptor: Handler<DeliveryContext<T>>?): EventBus {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> addInboundInterceptor(interceptor: Handler<DeliveryContext<T>>?): EventBus {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> removeInboundInterceptor(interceptor: Handler<DeliveryContext<T>>?): EventBus {
        TODO("Not yet implemented")
    }

    fun <T: Event> publishedEventsOfType(eventType: Class<T>): List<T> {
        return publishedEvents.getOrDefault(eventType.simpleName, listOf()).map { any -> any as T }
    }
}
