package racoony.software.klubi.domain.member_registration

import racoony.software.klubi.domain.bank.Bic
import racoony.software.klubi.domain.bank.IBAN
import racoony.software.klubi.domain.member_registration.AccountOwner

data class BankDetails(
    val accountOwner: AccountOwner,
    val iban: IBAN,
    val bic: Bic? = null
)
