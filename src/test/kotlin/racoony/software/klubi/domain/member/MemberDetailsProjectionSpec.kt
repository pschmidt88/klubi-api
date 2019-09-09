package racoony.software.klubi.domain.member

import io.kotlintest.matchers.collections.shouldContain
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec
import racoony.software.klubi.domain.member_registration.Address
import racoony.software.klubi.domain.member_registration.AssignedDepartment
import racoony.software.klubi.domain.member_registration.Contact
import racoony.software.klubi.domain.member_registration.Department
import racoony.software.klubi.domain.member_registration.EmailAddress
import racoony.software.klubi.domain.member_registration.MemberStatus
import racoony.software.klubi.domain.member_registration.Name
import racoony.software.klubi.domain.member_registration.PaymentMethod
import racoony.software.klubi.domain.member_registration.PersonalDetails
import racoony.software.klubi.domain.member_registration.events.AssignedToDepartment
import racoony.software.klubi.domain.member_registration.events.BankTransferPaymentMethodSelected
import racoony.software.klubi.domain.member_registration.events.PersonalDetailsAdded
import java.time.LocalDate

class MemberDetailsProjectionSpec : BehaviorSpec({
    Given("a completed member registration") {
        val history = listOf(
            PersonalDetailsAdded(PersonalDetails(
                Name("Paul", "Schmidt"),
                Address("Aschrottstraße", "4", "34119", "Kassel"),
                LocalDate.of(1988, 6, 16),
                Contact(email = EmailAddress("rookian@gmail.com"))
            )),
            AssignedToDepartment(AssignedDepartment(
                Department("football"),
                MemberStatus.ACTIVE,
                LocalDate.of(2019, 6, 1)
            )),
            BankTransferPaymentMethodSelected()
        )

        When("build member details from history") {
            val memberDetails = MemberDetails().apply {
                restoreFromHistory(history)
            }

            Then("member details holds members name") {
                memberDetails.name shouldBe Name("Paul", "Schmidt")
            }

            Then("member details holds members address") {
                memberDetails.address shouldBe Address("Aschrottstraße", "4", "34119", "Kassel")
            }

            Then("member details holds members birthday") {
                memberDetails.birthday shouldBe LocalDate.of(1988, 6, 16)
            }

            Then("member details hold members contact") {
                memberDetails.contact shouldBe Contact(email = EmailAddress("rookian@gmail.com"))
            }

            Then("member details holds assigned department") {
                memberDetails.departments shouldContain AssignedDepartment(Department("football"), MemberStatus.ACTIVE, LocalDate.of(2019,6,1))
            }

            Then ("member details holds bank transfer payment method") {
                memberDetails.paymentMethod shouldBe PaymentMethod.BANK_TRANSFER
            }
        }
    }
})