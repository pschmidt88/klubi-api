package racoony.software.klubi.domain.member_registration

import io.kotlintest.matchers.beEmpty
import io.kotlintest.matchers.collections.shouldHaveSize
import io.kotlintest.shouldBe
import io.kotlintest.shouldNot
import io.kotlintest.specs.BehaviorSpec
import racoony.software.klubi.domain.member_registration.events.AssignedToDepartment
import racoony.software.klubi.event_sourcing.Changes
import racoony.software.klubi.resource.requests.AssignedDepartment
import java.time.LocalDate

class AssigningDepartmentsSpec : BehaviorSpec({
    Given("a fresh member registration") {
        val member = MemberRegistration()
        When("assign to the football department as active member") {
            val footballDepartment = Department("Fußball")
            member.assignToDepartment(AssignedDepartment(footballDepartment, MemberStatus.ACTIVE, LocalDate.now()))

            val memberAssignedEvents = Changes(member).ofType(AssignedToDepartment::class.java)
            Then("member is assigned to a department") {
                memberAssignedEvents shouldNot beEmpty()
                memberAssignedEvents shouldHaveSize 1
            }

            Then("member is assigned to the football department") {
                memberAssignedEvents.first().assignedDepartment.department shouldBe footballDepartment
            }

            Then("member is an active member in this department") {
                memberAssignedEvents.first().assignedDepartment.memberStatus shouldBe MemberStatus.ACTIVE
            }

            Then("is a member in this department since ${LocalDate.now()}") {
                memberAssignedEvents.first().assignedDepartment.entryDate shouldBe LocalDate.now()
            }
        }
    }
})