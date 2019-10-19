package racoony.software.klubi.domain.member

import com.fasterxml.jackson.annotation.JsonProperty
import racoony.software.klubi.domain.member_registration.Address
import racoony.software.klubi.domain.member_registration.Contact
import racoony.software.klubi.domain.member_registration.Name
import java.time.LocalDate

data class MemberDetailsJsonRepresentation(
    private val name: Name,
    address: Address,
    parse: LocalDate,
    contact: Contact
) {
    @JsonProperty
    private val firstName: String = this.name.firstName
    @JsonProperty
    private val lastName: String = this.name.lastName
}
