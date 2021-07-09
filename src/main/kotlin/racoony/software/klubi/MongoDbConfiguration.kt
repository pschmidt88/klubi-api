package racoony.software.klubi

import io.smallrye.config.ConfigMapping

@ConfigMapping(prefix = "mongodb", namingStrategy = ConfigMapping.NamingStrategy.VERBATIM)
interface MongoDbConfiguration {
    fun connectionString(): String
}
