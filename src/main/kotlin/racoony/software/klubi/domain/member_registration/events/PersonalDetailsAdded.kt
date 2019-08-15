package racoony.software.klubi.domain.member_registration.events

import racoony.software.klubi.domain.member_registration.PersonalDetails
import racoony.software.klubi.event_sourcing.Event

class PersonalDetailsAdded(
    val personalDetails: PersonalDetails
) : Event()
