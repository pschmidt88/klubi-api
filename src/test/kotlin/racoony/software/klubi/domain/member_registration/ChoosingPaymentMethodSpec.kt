package racoony.software.klubi.domain.member_registration

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.beEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNot
import racoony.software.klubi.domain.bank.IBAN
import racoony.software.klubi.domain.member_registration.events.BankTransferPaymentMethodSelected
import racoony.software.klubi.domain.member_registration.events.DirectDebitPaymentMethodSelected
import racoony.software.klubi.event_sourcing.Changes

class ChoosingPaymentMethodSpec : BehaviorSpec({
    Given("Member registration") {
        val memberRegistration = MemberRegistration()
        When("Choose bank transfer as payment method") {
            memberRegistration.selectPaymentMethod(PaymentMethod.BANK_TRANSFER)

            Then("Bank transfer set as payment method") {
                val setPaymentMethodEvents =
                    Changes(memberRegistration).ofType(BankTransferPaymentMethodSelected::class.java)
                setPaymentMethodEvents shouldNot beEmpty()
                setPaymentMethodEvents shouldHaveSize 1
            }
        }

        When("Choose debit as payment method with valid bank details") {
            val bankDetails = BankDetails(
                    accountOwner = AccountOwner("Paul", "Schmidt"),
                    iban = IBAN("DE19500105174628554285")
            )
            memberRegistration.selectPaymentMethod(PaymentMethod.DEBIT, bankDetails)

            val paymentMethodSetEvents = Changes(memberRegistration).ofType(DirectDebitPaymentMethodSelected::class.java)
            Then("direct debit set as payment method") {
                paymentMethodSetEvents shouldNot beEmpty()
                paymentMethodSetEvents shouldHaveSize 1
            }

            Then("set payment method holds bank details") {
                val paymentMethodSet = paymentMethodSetEvents.first()
                paymentMethodSet.bankDetails shouldBe bankDetails
            }
        }
    }
})
