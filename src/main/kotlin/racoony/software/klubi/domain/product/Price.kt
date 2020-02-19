package racoony.software.klubi.domain.product

import java.util.Currency

data class Price(
    private val value: Int,
    private val currency: Currency
)
