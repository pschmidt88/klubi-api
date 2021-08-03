package racoony.software.klubi

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import io.quarkus.jackson.ObjectMapperCustomizer
import javax.inject.Singleton

@Singleton
class CustomObjectMapperCustomizer : ObjectMapperCustomizer {
    override fun customize(objectMapper: ObjectMapper?) {
        objectMapper?.apply {
            setSerializationInclusion(JsonInclude.Include.NON_NULL)
            setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
        }

    }
}