package racoony.software.klubi.domain.member_registration

class MembershipFeePayment(
    val paymentMethod: PaymentMethod,
    val bankDetails: BankDetails? = null
)
