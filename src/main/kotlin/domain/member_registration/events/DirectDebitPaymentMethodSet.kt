package racoony.software.klubi.domain.member_registration.events

import racoony.software.klubi.domain.member_registration.BankDetails
import racoony.software.klubi.event_sourcing.Event

class DirectDebitPaymentMethodSet(
    val bankDetails: BankDetails
) : Event()
