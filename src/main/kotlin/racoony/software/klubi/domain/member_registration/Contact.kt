package racoony.software.klubi.domain.member_registration

data class Contact(
    val phone: PhoneNumber? = null,
    val email: EmailAddress? = null
)
