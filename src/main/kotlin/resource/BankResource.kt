package racoony.software.klubi.resource

import racoony.software.klubi.domain.bank.BankCode
import racoony.software.klubi.domain.bank.BankQuery
import racoony.software.klubi.domain.bank.IBAN
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/api/domain.bank")
class BankResource(
    private val bankQuery: BankQuery
) {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun findByBankCode(
        @QueryParam("bankcode") bankCode: BankCode?,
        @QueryParam("iban") iban: IBAN?
    ): Response {
        // if both query params are set, we use iban
        val bankInformation = iban?.let {
            this.bankQuery.byIban(iban)
        } ?: bankCode?.let {
            this.bankQuery.byBankCode(bankCode)
        } ?: throw Error("wether bankcode nor iban given.")

        return Response.ok().entity(bankInformation).build()
    }
}
