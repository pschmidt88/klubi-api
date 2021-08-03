package racoony.software.klubi.ports.http.member.registration.requests

import com.fasterxml.jackson.annotation.JsonInclude
import racoony.software.klubi.domain.member_registration.AccountOwner
import racoony.software.klubi.domain.member_registration.Address
import racoony.software.klubi.domain.member_registration.AssignedDepartment
import racoony.software.klubi.domain.member_registration.BankDetails
import racoony.software.klubi.domain.member_registration.Bic
import racoony.software.klubi.domain.member_registration.Contact
import racoony.software.klubi.domain.member_registration.Department
import racoony.software.klubi.domain.member_registration.EmailAddress
import racoony.software.klubi.domain.member_registration.IBAN
import racoony.software.klubi.domain.member_registration.MemberStatus
import racoony.software.klubi.domain.member_registration.Name
import racoony.software.klubi.domain.member_registration.PhoneNumber
import java.time.LocalDate

@JsonInclude(JsonInclude.Include.NON_NULL)
class MemberRegistrationRequest(
    val firstName: String,
    val lastName: String,
    val streetAddress: String,
    val streetNumber: String,
    val postalCode: String,
    val city: String,
    val birthday: LocalDate,
    val phone: String?,
    val email: String?,
    val department: String,
    val entryDate: LocalDate,
    val memberStatus: String,
    val paymentMethod: String,
    val accountOwnerFirstName: String?,
    val accountOwnerLastName: String?,
    val iban: String?,
    val bic: Bic?
) {
    private fun email(): EmailAddress? = this.email?.ifBlank { null }?.let { EmailAddress(it) }
    private fun phone(): PhoneNumber? = this.phone?.ifBlank { null }?.let { PhoneNumber(it) }

    val address: Address = Address(
        this.streetAddress,
        this.streetNumber,
        this.postalCode,
        this.city
    )

    val name: Name = Name(this.firstName, this.lastName)

    val contact: Contact = Contact(phone(), email())

    val bankDetails: BankDetails?
        get() {
            if (this.accountOwnerFirstName != null && this.accountOwnerLastName != null && this.iban != null) {
                return BankDetails(
                    AccountOwner(this.accountOwnerFirstName, this.accountOwnerLastName),
                    IBAN(this.iban),
                    this.bic
                )
            }
            return null
        }

    private val accountOwner: AccountOwner?
        get() {
            if (this.accountOwnerLastName != null && this.accountOwnerFirstName != null) {
                return AccountOwner(this.accountOwnerFirstName, this.accountOwnerLastName)
            }
            return null
        }

    val assignedDepartment: AssignedDepartment = AssignedDepartment(
        Department(this.department),
        MemberStatus.valueOf(this.memberStatus.toUpperCase()),
        this.entryDate
    )
}
