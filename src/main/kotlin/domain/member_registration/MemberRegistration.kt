package racoony.software.klubi.domain.member_registration

import org.joda.time.LocalDate
import racoony.software.klubi.domain.member_registration.events.PersonalDetailsAdded
import racoony.software.klubi.domain.member_registration.events.AssignedToDepartment
import racoony.software.klubi.domain.member_registration.events.BankTransferPaymentMethodSet
import racoony.software.klubi.domain.member_registration.events.DirectDebitPaymentMethodSet
import racoony.software.klubi.event_sourcing.Aggregate

class MemberRegistration : Aggregate() {
    fun addPersonalDetails(personalDetails: PersonalDetails) {
        raise(PersonalDetailsAdded(personalDetails))
    }

    fun assignDepartment(department: Department, memberStatus: MemberStatus, now: LocalDate) {
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
