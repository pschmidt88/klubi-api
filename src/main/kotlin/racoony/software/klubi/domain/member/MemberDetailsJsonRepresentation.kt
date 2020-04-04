package racoony.software.klubi.domain.member

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate
import racoony.software.klubi.domain.member_registration.Address
import racoony.software.klubi.domain.member_registration.Contact
import racoony.software.klubi.domain.member_registration.Name

@JsonInclude(Include.NON_NULL)
data class MemberDetailsJsonRepresentation(
    private val name: Name,
    private val address: Address,
    private val dateOfBirth: LocalDate,
    private val contact: Contact
) {
    @JsonProperty
    private val firstName = this.name.firstName

    @JsonProperty
    private val lastName = this.name.lastName

    @JsonProperty
    private val streetAddress = this.address.streetAddress

    @JsonProperty
    private val streetNumber = this.address.streetNumber

    @JsonProperty
    private val postalCode = this.address.postalCode

    @JsonProperty
    private val city = this.address.city

    @JsonProperty
    private val birthday = this.dateOfBirth.toString()

    @JsonProperty
    private val emailAddress = this.contact.email?.toString()

    @JsonProperty
    private val phoneNumber = this.contact.phone?.toString()
}
