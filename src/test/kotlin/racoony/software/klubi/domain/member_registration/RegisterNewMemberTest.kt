package racoony.software.klubi.domain.member_registration

import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.date.beToday
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import racoony.software.klubi.domain.member_registration.events.MemberRegistered
import racoony.software.klubi.event_sourcing.Changes
import java.time.LocalDate

class RegisterNewMemberTest {

    @Test
    fun `Given basic registration data, when register new member, then new member registered`() {
        val memberRegistration = MemberRegistration()

        val personalDetails = PersonalDetails(
            Name("Paul", "Schmidt"),
            Address("Herlebergweg", "20", "34130", "Kassel"),
            LocalDate.of(1988, 6, 16),
            Contact(null, EmailAddress("rookian@gmail.com"))
        )

        val assignedDepartment = AssignedDepartment(
            Department("Fußball"),
            MemberStatus.ACTIVE,
            LocalDate.now()
        )

        val bankTransferPayment = MembershipFeePayment(PaymentMethod.BANK_TRANSFER)

        memberRegistration.registerNewMember(personalDetails, assignedDepartment, bankTransferPayment)

        val changes = Changes.madeTo(memberRegistration).ofType(MemberRegistered::class.java)
        changes shouldHaveSize 1
    }

    @Test
    fun `Given basic registration with bank transfer payment, when registered new member, then member registration details are be present`() {
        val memberRegistration = MemberRegistration()

        val personalDetails = PersonalDetails(
            Name("Paul", "Schmidt"),
            Address("Herlebergweg", "20", "34130", "Kassel"),
            LocalDate.of(1988, 6, 16),
            Contact(null, EmailAddress("rookian@gmail.com"))
        )

        val assignedDepartment = AssignedDepartment(
            Department("Fußball"),
            MemberStatus.ACTIVE,
            LocalDate.now()
        )

        val bankTransferPayment = MembershipFeePayment(PaymentMethod.BANK_TRANSFER)

        memberRegistration.registerNewMember(personalDetails, assignedDepartment, bankTransferPayment)

        val memberRegistered = Changes.madeTo(memberRegistration).ofType(MemberRegistered::class.java).first()
        memberRegistered.personalDetails.name shouldBe Name("Paul", "Schmidt")
        memberRegistered.personalDetails.address shouldBe Address("Herlebergweg", "20", "34130", "Kassel")
        memberRegistered.personalDetails.birthday shouldBe LocalDate.of(1988, 6, 16)
        memberRegistered.personalDetails.contact.phone shouldBe null
        memberRegistered.personalDetails.contact.email shouldBe EmailAddress("rookian@gmail.com")

        memberRegistered.assignedDepartment.department shouldBe Department("Fußball")
        memberRegistered.assignedDepartment.memberStatus shouldBe MemberStatus.ACTIVE
        memberRegistered.assignedDepartment.entryDate should beToday()

        memberRegistered.membershipFeePayment.paymentMethod shouldBe PaymentMethod.BANK_TRANSFER
        memberRegistered.membershipFeePayment.bankDetails shouldBe null
    }

    @Test
    fun `Given basic registration with direct debit payment, when registered new member, then member registration details are be present`() {
        val memberRegistration = MemberRegistration()

        val personalDetails = PersonalDetails(
            Name("Paul", "Schmidt"),
            Address("Herlebergweg", "20", "34130", "Kassel"),
            LocalDate.of(1988, 6, 16),
            Contact(null, EmailAddress("rookian@gmail.com"))
        )

        val assignedDepartment = AssignedDepartment(
            Department("Fußball"),
            MemberStatus.ACTIVE,
            LocalDate.now()
        )

        val bankTransferPayment = MembershipFeePayment(
            PaymentMethod.DEBIT,
            BankDetails(AccountOwner("Paul", "Schmidt"), IBAN("DE72500105171853965525"), Bic("INGDDEFFXXX"))
        )

        memberRegistration.registerNewMember(personalDetails, assignedDepartment, bankTransferPayment)

        val memberRegistered = Changes.madeTo(memberRegistration).ofType(MemberRegistered::class.java).first()
        memberRegistered.personalDetails.name shouldBe Name("Paul", "Schmidt")
        memberRegistered.personalDetails.address shouldBe Address("Herlebergweg", "20", "34130", "Kassel")
        memberRegistered.personalDetails.birthday shouldBe LocalDate.of(1988, 6, 16)
        memberRegistered.personalDetails.contact.phone shouldBe null
        memberRegistered.personalDetails.contact.email shouldBe EmailAddress("rookian@gmail.com")

        memberRegistered.assignedDepartment.department shouldBe Department("Fußball")
        memberRegistered.assignedDepartment.memberStatus shouldBe MemberStatus.ACTIVE
        memberRegistered.assignedDepartment.entryDate should beToday()

        memberRegistered.membershipFeePayment.paymentMethod shouldBe PaymentMethod.DEBIT
        memberRegistered.membershipFeePayment.bankDetails?.accountOwner shouldBe AccountOwner("Paul", "Schmidt")
        memberRegistered.membershipFeePayment.bankDetails?.iban shouldBe IBAN("DE72500105171853965525")
        memberRegistered.membershipFeePayment.bankDetails?.bic shouldBe Bic("INGDDEFFXXX")
    }

}