package racoony.software.klubi.domain.member_registration

import racoony.software.klubi.domain.bank.Bic
import racoony.software.klubi.domain.bank.IBAN

data class BankDetails(
    val accountOwner: AccountOwner,
    val iban: IBAN,
    val bic: Bic? = null
)

data class AccountOwner(
    val firstName: String,
    val lastName: String
)
