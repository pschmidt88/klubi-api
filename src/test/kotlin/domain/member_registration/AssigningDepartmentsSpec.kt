package racoony.software.klubi.domain.member_registration

import io.kotlintest.matchers.beEmpty
import io.kotlintest.matchers.collections.shouldHaveSize
import io.kotlintest.shouldBe
import io.kotlintest.shouldNot
import io.kotlintest.specs.BehaviorSpec
import org.joda.time.LocalDate
import racoony.software.klubi.domain.member_registration.events.AssignedToDepartment
import racoony.software.klubi.event_sourcing.Changes

class AssigningDepartmentsSpec : BehaviorSpec({
    Given("a fresh member registration") {
        val member = MemberRegistration()
        When("assign to the football department as active member") {
            val footballDepartment = Department("Fußball")
            member.assignDepartment(footballDepartment, MemberStatus.ACTIVE, LocalDate.now())

            Then("member is assigned to the football department") {
                val memberAssignedEvents = Changes(member).ofType(AssignedToDepartment::class.java)
                memberAssignedEvents shouldNot beEmpty()
                memberAssignedEvents shouldHaveSize 1
                memberAssignedEvents.first().department shouldBe footballDepartment
            }
        }
    }
})