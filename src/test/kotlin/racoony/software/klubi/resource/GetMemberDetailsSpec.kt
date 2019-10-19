package racoony.software.klubi.resource

import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec
import racoony.software.klubi.domain.member.MemberDetailsJsonRepresentation
import racoony.software.klubi.domain.member_registration.Address
import racoony.software.klubi.domain.member_registration.Contact
import racoony.software.klubi.domain.member_registration.EmailAddress
import racoony.software.klubi.domain.member_registration.Name
import racoony.software.klubi.event_sourcing.InMemoryEventStore
import racoony.software.klubi.event_sourcing.Stories
import racoony.software.klubi.resource.member.details.MembersResource
import java.time.LocalDate
import java.util.UUID

class GetMemberDetailsSpec : BehaviorSpec({
    Given("registered member") {
        val history = Stories.registeredMember()
        val memberId = UUID.randomUUID()
        val eventStore = InMemoryEventStore().apply { save(memberId, history) }

        When("asking resource to get member") {

            val response = MembersResource(eventStore).find(memberId)

            Then("member should be returned as json") {
                response.entity shouldBe MemberDetailsJsonRepresentation(
                    Name("Paul", "Schmidt"),
                    Address("Aschrottstraße", "4", "34119", "Kassel"),
                    LocalDate.parse("1988-06-16"),
                    Contact(email = EmailAddress("rookian@gmail.com"))
                )
            }
        }
    }
})