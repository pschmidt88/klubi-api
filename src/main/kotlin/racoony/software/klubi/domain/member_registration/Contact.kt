package racoony.software.klubi.domain.member_registration

import com.fasterxml.jackson.annotation.JsonInclude

data class Contact(
    val phone: PhoneNumber? = null,
    val email: EmailAddress? = null
)
