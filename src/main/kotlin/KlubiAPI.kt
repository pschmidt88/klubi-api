package racoony.software.klubi

import io.dropwizard.Application
import io.dropwizard.setup.Environment
import racoony.software.klubi.filter.DiagnosticContextFilter
import racoony.software.klubi.healthcheck.DefaultHealthCheck

class KlubiAPI : Application<KlubiConfiguration>() {
    override fun run(configuration: KlubiConfiguration, environment: Environment) {
        println("Booting Klubi API...")

        environment.jersey().register(DiagnosticContextFilter())
        environment.healthChecks().register("default", DefaultHealthCheck())
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            KlubiAPI().run(*args)
        }
    }

}