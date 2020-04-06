package racoony.software.klubi

import io.dropwizard.configuration.UndefinedEnvironmentVariableException
import io.github.cdimascio.dotenv.dotenv
import org.apache.commons.text.StrLookup

class DotEnvVariableLookup : StrLookup<Any>() {
    override fun lookup(key: String): String {
        return dotenv {
            this.ignoreIfMissing = true
        }[key]
            ?: throw UndefinedEnvironmentVariableException(
                "The environment variable '$key' is not defined; " +
                        "could not substitute the expression '\${$key}'."
            )
    }
}
