package racoony.software.klubi.domain.member_registration

import java.time.LocalDate

class PersonalDetails(
    val name: Name,
    val address: Address,
    val birthday: LocalDate,
    val contact: Contact
)
