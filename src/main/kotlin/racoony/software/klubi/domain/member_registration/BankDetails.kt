package racoony.software.klubi.domain.member_registration

data class BankDetails(
    val accountOwner: AccountOwner,
    val iban: IBAN,
    val bic: Bic? = null
)

data class IBAN(
    val value: String
)

data class Bic(
    val value: String
)