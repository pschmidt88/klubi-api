package racoony.software.klubi.domain.member_registration

import org.joda.time.LocalDate
import racoony.software.klubi.domain.member_registration.events.PersonalDetailsAdded
import racoony.software.klubi.domain.member_registration.events.AssignedToDepartment
import racoony.software.klubi.domain.member_registration.events.BankTransferPaymentMethodSet
import racoony.software.klubi.domain.member_registration.events.DirectDebitPaymentMethodSet
import racoony.software.klubi.event_sourcing.Aggregate
import java.util.UUID

class MemberRegistration(
    id: UUID = UUID.randomUUID()
) : Aggregate(id) {

    fun addPersonalDetails(personalDetails: PersonalDetails) {
        raise(PersonalDetailsAdded(personalDetails))
    }

    fun assignToDepartment(departmentAssignment: DepartmentAssignment) {
        this.assignToDepartment(departmentAssignment.department, departmentAssignment.memberStatus, departmentAssignment.entryDate)
    }

    fun assignToDepartment(department: Department, memberStatus: MemberStatus, now: LocalDate) {
        raise(AssignedToDepartment(department, memberStatus, now))
    }

    fun setPaymentMethod(method: PaymentMethod, bankDetails: BankDetails? = null) {
        when (method) {
            PaymentMethod.BANK_TRANSFER -> raise(
                    BankTransferPaymentMethodSet()
            )
            PaymentMethod.DEBIT -> {
                bankDetails?.let {
                    raise(DirectDebitPaymentMethodSet(it))
                } ?: throw NoBankDetails()
            }
        }
    }
}

class NoBankDetails : Exception()
