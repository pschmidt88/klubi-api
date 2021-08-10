package racoony.software.klubi.domain.member

import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import racoony.software.klubi.domain.member_registration.Address
import racoony.software.klubi.domain.member_registration.AssignedDepartment
import racoony.software.klubi.domain.member_registration.Contact
import racoony.software.klubi.domain.member_registration.Department
import racoony.software.klubi.domain.member_registration.EmailAddress
import racoony.software.klubi.domain.member_registration.MemberStatus
import racoony.software.klubi.domain.member_registration.Name
import racoony.software.klubi.domain.member_registration.PaymentMethod
import racoony.software.klubi.event_sourcing.Stories
import java.time.LocalDate


class MemberDetailsProjectionSpec {

    @Test
    fun `Given a completed member registration, when build member details from history, member details hold member information`() {
        val history = Stories.registeredMember()

        val memberDetails = MemberDetailsProjection().apply {
            restoreFromHistory(history)
        }

        memberDetails.name() shouldBe Name("Paul", "Schmidt")
        memberDetails.address() shouldBe Address("Aschrottstraße", "4", "34119", "Kassel")

        memberDetails.birthday() shouldBe LocalDate.of(1988, 6, 16)

        memberDetails.contact() shouldBe Contact(email = EmailAddress("rookian@gmail.com"))

        memberDetails.departments() shouldContain AssignedDepartment(
            Department("Fußball"),
            MemberStatus.ACTIVE,
            LocalDate.of(2019, 7, 1)
        )

        memberDetails.paymentMethod() shouldBe PaymentMethod.BANK_TRANSFER
    }
}

//            Then("member details json representation is valid") {
//                val expectedJson = fixture("paul.json")
//                val actualJson = jacksonObjectMapper().writeValueAsString(memberDetails.toJson())
//
//                actualJson shouldBe expectedJson
//            }
