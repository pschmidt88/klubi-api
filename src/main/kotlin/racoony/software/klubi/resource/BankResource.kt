package racoony.software.klubi.resource

import racoony.software.klubi.domain.bank.BankCode
import racoony.software.klubi.domain.bank.BankQuery
import racoony.software.klubi.domain.bank.IBAN
import racoony.software.klubi.domain.bank.NoBankInformationFound
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/api/bank")
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
        val bankInformation = try {
            iban?.let {
                this.bankQuery.byIban(iban)
            } ?: bankCode?.let {
                this.bankQuery.byBankCode(bankCode)
            } ?: throw Error("wether bankcode nor iban given.")
        } catch (noBankInformationFound: NoBankInformationFound) {
            return Response.status(Response.Status.NOT_FOUND).build()
        }

        return Response.ok().entity(bankInformation).build()
    }
}
