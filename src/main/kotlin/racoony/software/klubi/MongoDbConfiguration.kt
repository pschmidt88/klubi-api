package racoony.software.klubi

import io.micronaut.context.annotation.ConfigurationProperties

@ConfigurationProperties("mongodb")
class MongoDbConfiguration {
    lateinit var connectionString: String
}