package racoony.software.klubi.domain.bank

interface BankQuery {
    fun byBankCode(bankCode: BankCode): BankInformation?
    fun byIban(iban: IBAN): BankInformation?
}
