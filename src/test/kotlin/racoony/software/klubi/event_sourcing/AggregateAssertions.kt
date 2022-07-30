package racoony.software.klubi.event_sourcing

import strikt.api.Assertion

fun <A : Aggregate<*>> Assertion.Builder<A>.containsAppliedEventOfType(eventType: Class<DomainEvent>): Assertion.Builder<A> {
   assert("contains applied event of type ${eventType.simpleName}") {
       it.events.con
   }
}