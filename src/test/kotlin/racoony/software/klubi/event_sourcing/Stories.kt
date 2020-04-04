package racoony.software.klubi.event_sourcing

import java.time.LocalDate
import racoony.software.klubi.domain.member_registration.Address
import racoony.software.klubi.domain.member_registration.AssignedDepartment
import racoony.software.klubi.domain.member_registration.Contact
import racoony.software.klubi.domain.member_registration.Department
import racoony.software.klubi.domain.member_registration.EmailAddress
import racoony.software.klubi.domain.member_registration.MemberStatus
import racoony.software.klubi.domain.member_registration.Name
import racoony.software.klubi.domain.member_registration.PersonalDetails
import racoony.software.klubi.domain.member_registration.events.AssignedToDepartment
import racoony.software.klubi.domain.member_registration.events.BankTransferPaymentMethodSelected
import racoony.software.klubi.domain.member_registration.events.PersonalDetailsAdded

object Stories {
    fun registeredMember(): List<Event> {
        return listOf(
            PersonalDetailsAdded(
                PersonalDetails(
                    Name("Paul", "Schmidt"),
                    Address("Aschrottstraße", "4", "34119", "Kassel"),
                    LocalDate.parse("1988-06-16"),
                    Contact(email = EmailAddress("rookian@gmail.com"))
                )),
            AssignedToDepartment(
                AssignedDepartment(
                    Department("Fußball"),
                    MemberStatus.ACTIVE,
                    LocalDate.parse("2019-07-01")
                )),
            BankTransferPaymentMethodSelected()
        )
    }
}
