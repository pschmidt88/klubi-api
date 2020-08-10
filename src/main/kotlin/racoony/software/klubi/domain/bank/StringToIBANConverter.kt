package racoony.software.klubi.domain.bank

import io.micronaut.core.convert.ConversionContext
import io.micronaut.core.convert.TypeConverter
import java.util.Optional
import javax.inject.Singleton

@Singleton
class StringToIBANConverter : TypeConverter<String, IBAN> {
    override fun convert(
        value: String?,
        targetType: Class<IBAN>?,
        context: ConversionContext?
    ): Optional<IBAN> {
        value?.let {
            return Optional.of(IBAN(value))
        }
        return Optional.empty()
    }
}