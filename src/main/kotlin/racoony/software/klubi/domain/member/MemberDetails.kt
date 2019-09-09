package racoony.software.klubi.domain.member

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import racoony.software.klubi.domain.member_registration.Address
import racoony.software.klubi.domain.member_registration.AssignedDepartment
import racoony.software.klubi.domain.member_registration.Contact
import racoony.software.klubi.domain.member_registration.Name
import racoony.software.klubi.domain.member_registration.PaymentMethod
import racoony.software.klubi.domain.member_registration.events.AssignedToDepartment
import racoony.software.klubi.domain.member_registration.events.BankTransferPaymentMethodSelected
import racoony.software.klubi.domain.member_registration.events.PersonalDetailsAdded
import racoony.software.klubi.event_sourcing.Event
import java.time.LocalDate

class MemberDetails {
    private val logger: Logger = LoggerFactory.getLogger(MemberDetails::class.java)

    var name: Name? = null
    var address: Address? = null
    var birthday: LocalDate? = null
    var contact: Contact? = null
    val departments: MutableList<AssignedDepartment> = mutableListOf()
    var paymentMethod: PaymentMethod? = null

    private fun apply(event: BankTransferPaymentMethodSelected) {
        this.paymentMethod = PaymentMethod.BANK_TRANSFER
    }

    private fun apply(event: AssignedToDepartment) {
        this.departments.add(event.assignedDepartment)
    }

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
        } catch (_: NoSuchMethodException) {
            logger.debug("No method for ${event::class}")
        }
    }

    fun restoreFromHistory(history: List<Event>) {
        history.forEach {
            this.applyChange(it)
        }
    }
}