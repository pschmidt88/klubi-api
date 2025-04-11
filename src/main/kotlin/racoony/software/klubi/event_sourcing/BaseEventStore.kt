package racoony.software.klubi.event_sourcing

import arrow.core.Option
import arrow.core.getOrElse
import arrow.core.left
import arrow.core.right
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import racoony.software.klubi.domain.Result
import racoony.software.klubi.ports.bus.EventBus


/**
 * This base class takes away the complexity of dealing with versions and mapping the
 * event descriptor to the actual event from the actual store implementation (for example: mongodb)
 *
 * TODO: find a better solution than the nullable eventBus. we need to have a zero arg constructor for CDI to work
 */
abstract class BaseEventStore<E : EventDescriptor>(
    private val eventBus: EventBus? = null
) : EventStore {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(BaseEventStore::class.java)
    }

    protected abstract suspend fun stream(key: AggregateId): Option<Iterable<E>>
    protected abstract suspend fun appendEvent(key: AggregateId, version: Long, event: Event)


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
        aggregateId: AggregateId,
        events: Iterable<Event>,
        previousAggregateVersion: Option<Long>
    ): Iterable<Event> {
        val baseVersion = previousAggregateVersion.getOrElse { -1 }

        return flow {
            for ((i, event) in events.withIndex()) {
                val eventVersion = baseVersion + i + 1
                val versionedEvent = event.copyWithVersion(eventVersion)
                emit(versionedEvent)

                log.debug("Appending event {} to stream {}", versionedEvent, aggregateId)
                appendEvent(aggregateId, eventVersion, versionedEvent)

                log.trace("Publishing event {} on the bus", versionedEvent)
                eventBus?.publish(versionedEvent)
            }
        }.toList()
    }

    private fun Option<Iterable<EventDescriptor>>.versionMismatchDetected(expectedVersion: Option<Long>): Boolean {
        return expectedVersion.map { version ->
            val lastEvent = this.flatMap {
                eventDescriptors -> Option.fromNullable(eventDescriptors.lastOrNull())
            }

            lastEvent.isSome { event -> event.version != version }
        }.getOrElse { false }
    }
}

interface EventDescriptor {
    val aggregateId: AggregateId
    val event: Event
    val version: Long?
}
