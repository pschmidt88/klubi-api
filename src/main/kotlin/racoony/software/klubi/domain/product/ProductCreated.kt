package racoony.software.klubi.domain.product

import racoony.software.klubi.event_sourcing.Event

class ProductCreated(
    val name: String,
    val description: String,
    val productType: ProductType,
    val price: Price
) : Event
