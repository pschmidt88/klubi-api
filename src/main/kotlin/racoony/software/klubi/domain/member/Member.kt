package racoony.software.klubi.domain.member

import com.fasterxml.jackson.annotation.JsonProperty
import io.quarkus.runtime.annotations.RegisterForReflection
import java.time.LocalDate

@RegisterForReflection
data class Member(
    @field:JsonProperty("firstName")
    val firstName: String? = null,

    @field:JsonProperty("lastName")
    val lastName: String? = null,

    @field:JsonProperty("streetAddress")
    val streetAddress: String? = null,

    @field:JsonProperty("streetNumber")
    val streetNumber: String? = null,

    @field:JsonProperty("postalCode")
    val postalCode: String? = null,

    @field:JsonProperty("city")
    val city: String? = null,

    @JsonProperty("dateOfBirth")
    val dateOfBirth: LocalDate? = null,

    @JsonProperty("emailAddress")
    val emailAddress: String? = null,

    @JsonProperty("phoneNumber")
    val phoneNumber: String? = null
)
