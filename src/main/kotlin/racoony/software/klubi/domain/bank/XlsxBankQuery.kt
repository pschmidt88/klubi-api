package racoony.software.klubi.domain.bank

import org.apache.poi.ss.usermodel.WorkbookFactory

private const val BANKCODES_PATH = "/bankcodes/201907_blz-aktuell-xls-data.xlsx"

// TODO extract into own micro service
class XlsxBankQuery : BankQuery {
    private val bankCodesStore = mutableListOf<BankInformation>()

    init {
        val inputStream = XlsxBankQuery::class.java.getResourceAsStream(BANKCODES_PATH)
        WorkbookFactory.create(inputStream).also {

            it.getSheetAt(0).forEach { row ->
                if (row.rowNum == 0) {
                    return@forEach
                }

                val bankCode = BankCode(row.getCell(0).stringCellValue)
                val bankName = row.getCell(2).stringCellValue
                val shortBankName = row.getCell(5).stringCellValue
                val bic = Bic(row.getCell(7).stringCellValue)

                bankCodesStore.add(BankInformation(bankCode, BankName(shortBankName, bankName), bic))
            }
        }
    }

    override fun byIban(iban: IBAN): BankInformation? {
        return bankCodesStore.firstOrNull { it.bankCode == iban.bankCode }
    }

    override fun byBankCode(bankCode: BankCode): BankInformation? {
        return bankCodesStore.firstOrNull { it.bankCode == bankCode }
    }
}
