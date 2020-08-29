package racoony.software.klubi.domain.member_registration

import racoony.software.klubi.domain.member_registration.Address
import racoony.software.klubi.domain.member_registration.Contact
import racoony.software.klubi.domain.member_registration.Name
import java.time.LocalDate

class PersonalDetails(
    val name: Name,
    val address: Address,
    val birthday: LocalDate,
    val contact: Contact
)
