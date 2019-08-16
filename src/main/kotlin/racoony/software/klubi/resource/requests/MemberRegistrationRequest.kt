package racoony.software.klubi.resource.requests

import org.joda.time.LocalDate
import racoony.software.klubi.domain.member_registration.Address
import racoony.software.klubi.domain.member_registration.BankDetails
import racoony.software.klubi.domain.member_registration.DepartmentAssignment
import racoony.software.klubi.domain.member_registration.EmailAddress
import racoony.software.klubi.domain.member_registration.PaymentMethod
import racoony.software.klubi.domain.member_registration.PhoneNumber

class MemberRegistrationRequest(
    val firstName: String,
    val lastName: String,
    val address: Address,
    val birthday: LocalDate,
    val phone: PhoneNumber?,
    val email: EmailAddress?,
    val departments: List<DepartmentAssignment>,
    val paymentMethod: PaymentMethod,
    val bankDetails: BankDetails?
)
