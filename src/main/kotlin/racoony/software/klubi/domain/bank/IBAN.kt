package racoony.software.klubi.domain.bank

data class IBAN(
    val value: String
) {
    fun bankCode(): BankCode {
        if (this.value.startsWith("DE")) {
            return BankCode(this.value.substring(4, 12))
        }

        throw Error("I dont support non german IBANs for now.")
    }
}
