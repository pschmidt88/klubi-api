package racoony.software.klubi.domain.member_registration.events

import racoony.software.klubi.domain.member_registration.BankDetails
import racoony.software.klubi.event_sourcing.Event

class DirectDebitPaymentMethodSelected(
    val bankDetails: BankDetails
) : Event()
