package racoony.software.klubi.domain.bank

import org.apache.poi.ss.usermodel.WorkbookFactory
import javax.inject.Singleton
import kotlin.Exception

private const val BANKCODES_PATH = "/bankcodes/201907_blz-aktuell-xls-data.xlsx"

@Singleton
class XlsxBankQuery : BankQuery {
    private val bankCodesStore: List<BankInformation>
    private val inputStream = XlsxBankQuery::class.java.getResourceAsStream(BANKCODES_PATH)

    init {
        bankCodesStore = WorkbookFactory.create(inputStream)
                .getSheetAt(0)
                .filter { row -> row.rowNum > 0 }
                .map { row ->
                    val bankCode = BankCode(row.getCell(0).stringCellValue)
                    val bic = Bic(row.getCell(7).stringCellValue)
                    val bankName = BankName(
                            row.getCell(5).stringCellValue,
                            row.getCell(2).stringCellValue
                    )
                    BankInformation(bankCode, bankName, bic)
                }.toList()
    }

    override fun byIban(iban: IBAN): BankInformation {
        return bankCodesStore.firstOrNull { it.bankCode == iban.bankCode() } ?: throw NoBankInformationFound()
    }

    override fun byBankCode(bankCode: BankCode): BankInformation {
        return bankCodesStore.firstOrNull { it.bankCode == bankCode } ?: throw NoBankInformationFound()
    }
}

class NoBankInformationFound : Exception()