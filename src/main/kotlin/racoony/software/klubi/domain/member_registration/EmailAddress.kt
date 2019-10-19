package racoony.software.klubi.domain.member_registration

data class EmailAddress(
    val emailAddress: String
) {
    override fun toString(): String = this.emailAddress
}
