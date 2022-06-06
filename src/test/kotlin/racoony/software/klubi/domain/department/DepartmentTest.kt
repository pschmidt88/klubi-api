package racoony.software.klubi.domain.department

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import racoony.software.klubi.event_sourcing.Changes

class DepartmentTest {

    @Test
    internal fun `creates a department and raises event`() {

        val footballDepartment = Department.create(DepartmentName("Fußball"))


        Changes.madeTo(footballDepartment).ofType(DepartmentCreated::class.java)
    }
}