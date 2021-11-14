package racoony.software.klubi.domain.member

import racoony.software.klubi.domain.member_registration.Address
import racoony.software.klubi.domain.member_registration.AssignedDepartment
import racoony.software.klubi.domain.member_registration.Contact
import racoony.software.klubi.domain.member_registration.EmailAddress
import racoony.software.klubi.domain.member_registration.events.MemberRegistered
import racoony.software.klubi.domain.member_registration.Name
import racoony.software.klubi.domain.member_registration.PaymentMethod
import racoony.software.klubi.event_sourcing.Event
import java.time.LocalDate

class MemberProjection {
    private lateinit var name: Name
    private lateinit var address: Address
    private lateinit var birthday: LocalDate
    private lateinit var contact: Contact
    private val departments: MutableList<AssignedDepartment> = mutableListOf()
    private lateinit var paymentMethod: PaymentMethod

    private fun applyChange(event: Event) {
        try {
            this.javaClass.getDeclaredMethod("apply", event.javaClass).also {
                it.isAccessible = true
                it.invoke(this, event)
            }
        } catch (_: NoSuchMethodException) {}
    }

    fun apply(event: MemberRegistered) {
        this.name = event.personalDetails.name
        this.address = event.personalDetails.address
        this.birthday = event.personalDetails.birthday
        this.contact = event.personalDetails.contact
        this.departments.add(event.assignedDepartment)
        this.paymentMethod = event.membershipFeePayment.paymentMethod
    }

    fun restoreFromHistory(history: List<Event>) {
        history.forEach {
            this.applyChange(it)
        }
    }

    fun name(): Name = this.name

    fun address(): Address = this.address

    fun birthday(): LocalDate = this.birthday

    fun contact(): Contact = this.contact

    fun departments(): List<AssignedDepartment> = this.departments

    fun paymentMethod(): PaymentMethod = this.paymentMethod

    fun toMember(): Member {
        return Member(
            firstName = name.firstName,
            lastName = name.lastName,
            streetAddress = address.streetAddress,
            streetNumber = address.streetNumber,
            postalCode = address.postalCode,
            city = address.city,
            dateOfBirth = birthday,
            emailAddress = contact.email?.toString(),
            phoneNumber = contact.phone?.toString()
        )
    }
}