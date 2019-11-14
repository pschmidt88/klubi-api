package racoony.software.klubi

import org.apache.commons.text.StrSubstitutor

class DotEnvEnvironmentVariableSubstitutor(
    private val substituteInVariables: Boolean = false
) : StrSubstitutor(DotEnvVariableLookup()) {
    init {
        this.isEnableSubstitutionInVariables = this.substituteInVariables
    }
}
