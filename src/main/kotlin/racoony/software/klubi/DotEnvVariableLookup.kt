package racoony.software.klubi

import io.dropwizard.configuration.UndefinedEnvironmentVariableException
import org.apache.commons.text.StrLookup
import io.github.cdimascio.dotenv.dotenv

class DotEnvVariableLookup() : StrLookup<Any>() {
    override fun lookup(key: String): String {
        return dotenv()[key]
            ?: throw UndefinedEnvironmentVariableException("The environment variable '$key' is not defined; " +
                    "could not substitute the expression '\${$key}'.")
    }
}