package racoony.software.klubi.event_sourcing

import arrow.core.Option
import arrow.core.getOrElse
import arrow.core.left
import arrow.core.raise.nullable
import arrow.core.right
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import racoony.software.klubi.domain.Result
import racoony.software.klubi.ports.bus.EventBus
import java.util.UUID


/**
 * This base class takes away the complexity of dealing with versions and mapping the
 * event descriptor to the actual event from the actual store implementation (for example: mongodb)
 *
 * TODO: find a better solution than the nullable eventBus. we need to have a zero arg constructor for CDI to work
 */
abstract class BaseEventStore(
    private val eventBus: EventBus? = null
) : EventStore {

    data class EventDescriptor(val aggregateId: AggregateId, val version: Long, val event: Event)

    companion object {
        private val log: Logger = LoggerFactory.getLogger(BaseEventStore::class.java)
    }

    protected abstract suspend fun stream(key: AggregateId): Option<Iterable<EventDescriptor>>
    protected abstract suspend fun appendEventDescriptor(key: AggregateId, eventDescriptor: EventDescriptor)


    override suspend fun findEventsByAggregateId(aggregateId: AggregateId): Option<Iterable<Event>> {
        log.debug("Retrieving events for aggregate id {}", aggregateId)
        return stream(aggregateId).map { it.map { eventDescriptor -> eventDescriptor.event } }
    }

    override suspend fun saveEvents(
        aggregateId: AggregateId,
        events: Iterable<Event>,
        expectedVersion: Option<Long>
    ): Result<EventStoreFailure, Iterable<Event>> {
        log.debug("Saving new events for aggregate id {}. Expected version: {}", aggregateId, expectedVersion)

        if (stream(aggregateId).versionMismatchDetected(expectedVersion)) {
            log.debug("Version mismatch detected")
            return EventStoreFailure.VersionMismatch.left()
        }

        log.debug("Appending and publishing {} new events", events.count())
        return appendAndPublish(aggregateId, events, expectedVersion).right()
    }

    private suspend fun appendAndPublish(
        aggregateId: UUID,
        events: Iterable<Event>,
        previousAggregateVersion: Option<Long>
    ): Iterable<Event> {
        val baseVersion = previousAggregateVersion.getOrElse { -1 }

        return flow {
            for ((i, event) in events.withIndex()) {
                val eventVersion = baseVersion + i + 1
                val versionedEvent = event.copyWithVersion(eventVersion)
                emit(versionedEvent)

                val eventDescriptor = EventDescriptor(aggregateId, eventVersion, event)
                log.debug("Appending event {} to stream {}", versionedEvent, aggregateId)
                appendEventDescriptor(aggregateId, eventDescriptor)

                log.trace("Publishing event {} on the bus", versionedEvent)
                eventBus?.publish(event)
            }
        }.toList()
    }

    private fun Option<Iterable<EventDescriptor>>.versionMismatchDetected(expectedVersion: Option<Long>): Boolean {
        return expectedVersion.map { version ->
            this.map { eventDescriptors -> eventDescriptors.lastOrNull() }
                .isSome { event -> event?.version != version }
        }.getOrElse { false }
    }
}