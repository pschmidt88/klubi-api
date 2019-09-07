package racoony.software.klubi.domain.member_registration

import racoony.software.klubi.domain.member_registration.events.PersonalDetailsAdded
import racoony.software.klubi.domain.member_registration.events.AssignedToDepartment
import racoony.software.klubi.domain.member_registration.events.BankTransferPaymentMethodSelected
import racoony.software.klubi.domain.member_registration.events.DirectDebitPaymentMethodSelected
import racoony.software.klubi.event_sourcing.Aggregate
import racoony.software.klubi.resource.requests.AssignedDepartment
import java.util.UUID

class MemberRegistration(
    id: UUID = UUID.randomUUID()
) : Aggregate(id) {

    fun addPersonalDetails(personalDetails: PersonalDetails) {
        raise(PersonalDetailsAdded(personalDetails))
    }

    fun assignToDepartment(assignedDepartment: AssignedDepartment) {
        raise(AssignedToDepartment(assignedDepartment))
    }

    fun setPaymentMethod(method: PaymentMethod, bankDetails: BankDetails? = null) {
        when (method) {
            PaymentMethod.BANK_TRANSFER -> raise(
                BankTransferPaymentMethodSelected()
            )
            PaymentMethod.DEBIT -> {
                bankDetails?.let {
                    raise(DirectDebitPaymentMethodSelected(it))
                } ?: throw NoBankDetails()
            }
        }
    }
}

class NoBankDetails : Exception()
