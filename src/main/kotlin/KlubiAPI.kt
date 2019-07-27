package racoony.software.klubi

import io.dropwizard.Application
import io.dropwizard.jersey.setup.JerseyEnvironment
import io.dropwizard.setup.Environment
import racoony.software.klubi.domain.bank.XlsxBankQuery
import racoony.software.klubi.filter.DiagnosticContextFilter
import racoony.software.klubi.healthcheck.DefaultHealthCheck
import racoony.software.klubi.resource.BankResource

class KlubiAPI : Application<KlubiConfiguration>() {
    override fun run(configuration: KlubiConfiguration, environment: Environment) {
        println("Booting Klubi API...")

        registerApiEndpoints(environment.jersey())

        environment.jersey().register(DiagnosticContextFilter())
        environment.healthChecks().register("default", DefaultHealthCheck())
    }

    private fun registerApiEndpoints(jersey: JerseyEnvironment) {
        jersey.register(BankResource(XlsxBankQuery()))
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            KlubiAPI().run(*args)
        }
    }
}
