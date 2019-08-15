package racoony.software.klubi.domain.bank

data class BankInformation(
    val bankCode: BankCode,
    val bankName: BankName,
    val bic: Bic
)
