package racoony.software.klubi.domain.department

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.first
import strikt.assertions.isA

class DepartmentTest {

    @Test
    fun `creates a department and raises event`() {
        val footballDepartment = Department.create(DepartmentName("Fußball"))

        expectThat(footballDepartment.events).first().isA<DepartmentCreated>()
    }
}