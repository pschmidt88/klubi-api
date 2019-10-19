package racoony.software.klubi.domain.member_registration

data class PhoneNumber(
    val number: String
) {
    override fun toString(): String = this.number
}
