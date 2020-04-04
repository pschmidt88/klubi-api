package racoony.software.klubi.domain.bank

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class XlsxBankQuerySpec : ShouldSpec({
    "XlsxBankQuery::byBankCode with 50010517" {
        val bankInformation = XlsxBankQuery().byBankCode(BankCode("50010517"))

        should("return bankinformation for ing diba") {
            bankInformation shouldNotBe null
            bankInformation.bankName.name shouldBe "ING-DiBa"
        }
    }

    "XlsxBankQuery::byIban with DE78500105175419262594" {
        val bankInformation = XlsxBankQuery().byIban(IBAN("DE78500105175419262594"))
        should("return bankinformation for ing diba") {
            bankInformation shouldNotBe null
            bankInformation.bankName.name shouldBe "ING-DiBa"
        }
    }
})
