package racoony.software.klubi.event_sourcing

import java.time.LocalDate
import racoony.software.klubi.domain.member_registration.Address
import racoony.software.klubi.domain.member_registration.AssignedDepartment
import racoony.software.klubi.domain.member_registration.Contact
import racoony.software.klubi.domain.member_registration.Department
import racoony.software.klubi.domain.member_registration.EmailAddress
import racoony.software.klubi.domain.member_registration.events.MemberRegistered
import racoony.software.klubi.domain.member_registration.MemberStatus
import racoony.software.klubi.domain.member_registration.MembershipFeePayment
import racoony.software.klubi.domain.member_registration.Name
import racoony.software.klubi.domain.member_registration.PaymentMethod
import racoony.software.klubi.domain.member_registration.PersonalDetails

object Stories {
    fun registeredMember(): List<Event> {
        return listOf(
            MemberRegistered(
                PersonalDetails(
                    Name("Paul", "Schmidt"),
                    Address("Aschrottstraße", "4", "34119", "Kassel"),
                    LocalDate.parse("1988-06-16"),
                    Contact(email = EmailAddress("rookian@gmail.com"))
                ),
                AssignedDepartment(
                    Department("Fußball"),
                    MemberStatus.ACTIVE,
                    LocalDate.parse("2019-07-01")
                ),
                MembershipFeePayment(PaymentMethod.BANK_TRANSFER)
            )
        )
    }
}
