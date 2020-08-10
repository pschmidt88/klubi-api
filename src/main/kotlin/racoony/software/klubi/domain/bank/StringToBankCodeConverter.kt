package racoony.software.klubi.domain.bank

import io.micronaut.core.convert.ConversionContext
import io.micronaut.core.convert.TypeConverter
import java.util.Optional
import javax.inject.Singleton

@Singleton
class StringToBankCodeConverter : TypeConverter<String, BankCode> {
    override fun convert(
        stringValue: String?,
        type: Class<BankCode>,
        context: ConversionContext?
    ): Optional<BankCode> {
        stringValue?.let {
            return Optional.of(BankCode(it))
        }
        return Optional.empty()
    }
}