package racoony.software.klubi

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.dropwizard.Application
import io.dropwizard.setup.Environment
import org.litote.kmongo.KMongo
import racoony.software.klubi.domain.bank.XlsxBankQuery
import racoony.software.klubi.adapter.mongodb.MongoDBEventStore
import racoony.software.klubi.filter.DiagnosticContextFilter
import racoony.software.klubi.healthcheck.DefaultHealthCheck
import racoony.software.klubi.resource.BankResource
import racoony.software.klubi.resource.member.registration.MembersRegistrationResource
import com.mongodb.MongoClientURI
import io.dropwizard.configuration.EnvironmentVariableSubstitutor
import io.dropwizard.configuration.SubstitutingSourceProvider
import io.dropwizard.jersey.jackson.JsonProcessingExceptionMapper
import io.dropwizard.setup.Bootstrap
import racoony.software.klubi.domain.member_registration.MemberRegistration
import racoony.software.klubi.event_sourcing.AggregateRepository
import racoony.software.klubi.adapter.rx.RxEventBus
import racoony.software.klubi.resource.member.details.MembersResource

class KlubiAPI : Application<KlubiConfiguration>() {
    override fun initialize(bootstrap: Bootstrap<KlubiConfiguration>) {
        bootstrap.configurationSourceProvider = SubstitutingSourceProvider(
            bootstrap.configurationSourceProvider,
            EnvironmentVariableSubstitutor()
        )
        this.configureObjectMapper(bootstrap.objectMapper)
    }

    private fun configureObjectMapper(objectMapper: ObjectMapper) {
        objectMapper.registerKotlinModule()
    }

    override fun run(configuration: KlubiConfiguration, environment: Environment) {
        println("Booting Klubi API...")

        val uri = MongoClientURI(configuration.mongoDbConnection)
        val eventStore = MongoDBEventStore(KMongo.createClient(uri))

        environment.jersey().register(JsonProcessingExceptionMapper(true))
        environment.jersey().register(DiagnosticContextFilter())
        environment.healthChecks().register("default", DefaultHealthCheck())

        val eventBus = RxEventBus()

        val memberRegistrations = AggregateRepository<MemberRegistration>(eventStore, eventBus)

        environment.jersey().register(BankResource(XlsxBankQuery()))
        environment.jersey().register(MembersRegistrationResource(memberRegistrations))
        environment.jersey().register(MembersResource(eventStore))
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            KlubiAPI().run(*args)
        }
    }
}
