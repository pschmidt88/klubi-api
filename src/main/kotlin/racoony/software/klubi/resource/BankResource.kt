package racoony.software.klubi.resource

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.micronaut.http.annotation.QueryValue
import racoony.software.klubi.domain.bank.BankCode
import racoony.software.klubi.domain.bank.BankQuery
import racoony.software.klubi.domain.bank.IBAN
import racoony.software.klubi.domain.bank.NoBankInformationFound

@Controller("/api/bank")
class BankResource(
    private val bankQuery: BankQuery
) {
    @Get
    @Produces(MediaType.APPLICATION_JSON)
    fun findByBankCode(
        @QueryValue bankCode: BankCode?,
        @QueryValue iban: IBAN?
    ): HttpResponse<*> {
        // if both query params are set, we use iban
        val bankInformation = try {
            iban?.let {
                this.bankQuery.byIban(iban)
            } ?: bankCode?.let {
                this.bankQuery.byBankCode(bankCode)
            } ?: throw Error("wether bankcode nor iban given.")
        } catch (noBankInformationFound: NoBankInformationFound) {
            return HttpResponse.notFound<Any>()
        }

        return HttpResponse.ok(bankInformation)
    }
}
