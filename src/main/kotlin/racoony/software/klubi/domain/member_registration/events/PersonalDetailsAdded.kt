package racoony.software.klubi.domain.member_registration.events

import com.fasterxml.jackson.annotation.JsonInclude
import racoony.software.klubi.domain.member_registration.PersonalDetails
import racoony.software.klubi.event_sourcing.Event

class PersonalDetailsAdded(
    val personalDetails: PersonalDetails
) : Event
