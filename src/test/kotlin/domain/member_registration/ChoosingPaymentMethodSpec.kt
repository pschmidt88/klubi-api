package racoony.software.klubi.domain.member_registration

import io.kotlintest.matchers.beEmpty
import io.kotlintest.specs.BehaviorSpec
import racoony.software.klubi.domain.bank.IBAN
import io.kotlintest.matchers.collections.shouldHaveSize
import io.kotlintest.shouldBe
import io.kotlintest.shouldNot
import racoony.software.klubi.domain.member_registration.events.BankTransferPaymentMethodSet
import racoony.software.klubi.domain.member_registration.events.DirectDebitPaymentMethodSet
import racoony.software.klubi.event_sourcing.Changes

class ChoosingPaymentMethodSpec : BehaviorSpec({
    Given("Member registration") {
        val memberRegistration = MemberRegistration()
        When("Choose bank transfer as payment method") {
            memberRegistration.setPaymentMethod(PaymentMethod.BANK_TRANSFER)

            Then("Bank transfer set as payment method") {
                val setPaymentMethodEvents =
                    Changes(memberRegistration).ofType(BankTransferPaymentMethodSet::class.java)
                setPaymentMethodEvents shouldNot beEmpty()
                setPaymentMethodEvents shouldHaveSize 1
            }
        }

        When("Choose debit as payment method with valid bank details") {
            val bankDetails = BankDetails(
                accountOwner = AccountOwner("Paul", "Schmidt"),
                iban = IBAN("DE19500105174628554285")
            )
            memberRegistration.setPaymentMethod(PaymentMethod.DEBIT, bankDetails)

            val paymentMethodSetEvents = Changes(memberRegistration).ofType(DirectDebitPaymentMethodSet::class.java)
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