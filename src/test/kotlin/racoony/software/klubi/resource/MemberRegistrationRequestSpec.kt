package racoony.software.klubi.resource

import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.dropwizard.jackson.Jackson
import io.dropwizard.testing.FixtureHelpers.fixture
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import racoony.software.klubi.domain.bank.Bic
import racoony.software.klubi.domain.bank.IBAN
import racoony.software.klubi.domain.member_registration.AccountOwner
import racoony.software.klubi.domain.member_registration.Address
import racoony.software.klubi.domain.member_registration.Department
import racoony.software.klubi.domain.member_registration.EmailAddress
import racoony.software.klubi.domain.member_registration.MemberStatus
import racoony.software.klubi.domain.member_registration.Name
import racoony.software.klubi.domain.member_registration.PaymentMethod
import racoony.software.klubi.domain.member_registration.PhoneNumber
import racoony.software.klubi.domain.member_registration.AssignedDepartment
import racoony.software.klubi.resource.member.registration.requests.MemberRegistrationRequest
import java.time.LocalDate

class MemberRegistrationRequestSpec : ShouldSpec({
    val mapper = Jackson.newObjectMapper().registerKotlinModule()

    "deserializes minimum json" {
        val json = fixture("fixtures/requests/member_minimum.json")
        val request = mapper.readValue(json, MemberRegistrationRequest::class.java)

        should("contain the name Paul Schmidt") {
            request.name() shouldBe Name("Paul", "Schmidt")
        }

        should("contain address") {
            request.address() shouldBe Address("Aschrottstraße", "4", "34119", "Kassel")
        }

        should("contain birthday") {
            request.birthday() shouldBe LocalDate.of(1988, 6, 16)
        }

        should("contain football department") {
            request.assignedDepartment() shouldBe AssignedDepartment(
                Department("football"),
                MemberStatus.ACTIVE,
                LocalDate.of(2019, 9, 7)
            )
        }

        should("contain payment method type bank transfer") {
            request.paymentMethod() shouldBe PaymentMethod.BANK_TRANSFER
        }

        should("not contain any contact") {
            request.contact().phone shouldBe null
            request.contact().email shouldBe null
        }
        should("not contain any bank details") {
            request.bankDetails() shouldBe null
        }
    }

    "deserializes json with bankdetails and contact" {
        val json = fixture("fixtures/requests/member_full.json")
        val request = mapper.readValue(json, MemberRegistrationRequest::class.java)

        should("contain bank details") {
            request.bankDetails() shouldNotBe null
            request.bankDetails()?.accountOwner shouldBe AccountOwner("Paul", "Schmidt")
            request.bankDetails()?.bic shouldBe Bic("SOMEBIC")
            request.bankDetails()?.iban shouldBe IBAN("DE78500105175419262594")
        }

        should("contain contact details") {
            request.contact().email shouldBe EmailAddress("rookian@gmail.com")
            request.contact().phone shouldBe PhoneNumber("01711234567")
        }
    }
})
