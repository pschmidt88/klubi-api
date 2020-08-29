package racoony.software.klubi.domain.member

import racoony.software.klubi.domain.member_registration.Address
import racoony.software.klubi.domain.member_registration.AssignedDepartment
import racoony.software.klubi.domain.member_registration.Contact
import racoony.software.klubi.domain.member_registration.EmailAddress
import racoony.software.klubi.domain.member_registration.Name
import racoony.software.klubi.domain.member_registration.PaymentMethod
import racoony.software.klubi.domain.member_registration.events.AssignedToDepartment
import racoony.software.klubi.domain.member_registration.events.BankTransferPaymentMethodSelected
import racoony.software.klubi.domain.member_registration.events.PersonalDetailsAdded
import racoony.software.klubi.event_sourcing.Event
import java.time.LocalDate

class MemberDetailsProjection {
    private lateinit var name: Name
    private lateinit var address: Address
    private lateinit var birthday: LocalDate
    private lateinit var contact: Contact
    private val departments: MutableList<AssignedDepartment> = mutableListOf()
    private lateinit var paymentMethod: PaymentMethod

    @Suppress("unused")
    private fun apply(event: BankTransferPaymentMethodSelected) {
        this.paymentMethod = PaymentMethod.BANK_TRANSFER
    }

    @Suppress("unused")
    private fun apply(event: AssignedToDepartment) {
        this.departments.add(event.assignedDepartment)
    }

    @Suppress("unused")
    private fun apply(event: PersonalDetailsAdded) {
        this.name = event.personalDetails.name
        this.address = event.personalDetails.address
        this.birthday = event.personalDetails.birthday
        this.contact = event.personalDetails.contact
    }

    private fun applyChange(event: Event) {
        try {
            this.javaClass.getDeclaredMethod("apply", event.javaClass).also {
                it.isAccessible = true
                it.invoke(this, event)
            }
        } catch (_: NoSuchMethodException) {}
    }

    fun restoreFromHistory(history: List<Event>) {
        history.forEach {
            this.applyChange(it)
        }
    }

    fun toJson(): MemberDetailsJsonRepresentation {
        return MemberDetailsJsonRepresentation(
            this.name(),
            Address("Aschrottstraße", "4", "34119", "Kassel"),
            LocalDate.parse("1988-06-16"),
            Contact(email = EmailAddress("rookian@gmail.com"))
        )
    }

    fun name(): Name = this.name

    fun address(): Address = this.address

    fun birthday(): LocalDate = this.birthday

    fun contact(): Contact = this.contact

    fun departments(): List<AssignedDepartment> = this.departments

    fun paymentMethod(): PaymentMethod = this.paymentMethod
}