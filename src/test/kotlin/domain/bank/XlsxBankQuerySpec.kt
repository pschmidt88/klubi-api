package racoony.software.klubi.domain.bank

import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.ShouldSpec

class XlsxBankQuerySpec : ShouldSpec({
    "XlsxBankQuery::byBankCode with 50010517" {
        val bankInformation = XlsxBankQuery().byBankCode(BankCode("50010517"))

        should("return bankinformation for ing diba") {
            bankInformation shouldNotBe null
            bankInformation?.bankName?.name shouldBe "ING-DiBa"
        }
    }

    "XlsxBankQuery::byIban with DE78500105175419262594" {
        val bankInformation = XlsxBankQuery().byIban(IBAN("DE78500105175419262594"))
        should("return bankinformation for ing diba") {
            bankInformation shouldNotBe null
            bankInformation?.bankName?.name shouldBe "ING-DiBa"
        }
    }
})
