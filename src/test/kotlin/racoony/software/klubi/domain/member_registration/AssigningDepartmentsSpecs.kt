package racoony.software.klubi.domain.member_registration

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.beEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNot
import java.time.LocalDate
import racoony.software.klubi.domain.member_registration.events.AssignedToDepartment
import racoony.software.klubi.event_sourcing.Changes

class AssigningDepartmentsSpecs : BehaviorSpec({
    Given("a fresh member registration") {
        val member = MemberRegistration()
        When("assign to the football department as active member") {
            val footballDepartment = Department("Fußball")
            member.assignToDepartment(
                AssignedDepartment(
                    footballDepartment,
                    MemberStatus.ACTIVE,
                    LocalDate.now()
                )
            )

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
