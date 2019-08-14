package racoony.software.klubi.domain.member_registration

import org.joda.time.LocalDate

class PersonalDetails(
    val firstName: String,
    val lastName: String,
    val address: Address,
    val birthday: LocalDate,
    val phone: PhoneNumber?,
    val email: EmailAddress?
)

data class EmailAddress(
    val value: String
)

data class PhoneNumber(
    val value: String
)

data class Address(
    val streetAddress: String,
    val streetNumber: String,
    val postalCode: String,
    val city: String
)
