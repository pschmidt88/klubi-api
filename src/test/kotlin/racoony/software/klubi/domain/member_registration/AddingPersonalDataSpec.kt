package racoony.software.klubi.domain.member_registration

import io.kotlintest.specs.BehaviorSpec
import io.kotlintest.matchers.beEmpty
import io.kotlintest.matchers.collections.shouldHaveSize
import io.kotlintest.shouldBe
import io.kotlintest.shouldNot
import racoony.software.klubi.domain.member_registration.events.PersonalDetailsAdded
import racoony.software.klubi.event_sourcing.Changes
import java.time.LocalDate

class AddingPersonalDataSpec : BehaviorSpec({
    Given("A fresh member registration") {
        val member = MemberRegistration()
        When("Submitting personal data") {
            val originalPersonalDetails = PersonalDetails(
                    Name("Paul", "Schmidt"),
                    Address("Aschrottstraße", "4", "34119", "Kassel"),
                    LocalDate.of(1988, 6, 16),
                    Contact(null, EmailAddress("rookian@gmail.com"))
            )

            member.addPersonalDetails(originalPersonalDetails)

            Then("Personal details added") {
                val personalDetailsAddedEvents = Changes(member).ofType(PersonalDetailsAdded::class.java)
                personalDetailsAddedEvents shouldNot beEmpty()
                personalDetailsAddedEvents shouldHaveSize 1
                personalDetailsAddedEvents.first().personalDetails shouldBe originalPersonalDetails
            }
        }
    }
})