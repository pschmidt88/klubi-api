package racoony.software.klubi.domain.product

import racoony.software.klubi.event_sourcing.Aggregate
import java.util.UUID

class Product(id : UUID = UUID.randomUUID()) : Aggregate(id) {
        companion object {
        fun create(
            name: String,
            description: String,
            productType: ProductType,
            price: Price
        ): Product {
            return Product().apply {
                this.raise(ProductCreated(name, description, productType, price))
            }
        }
    }
}
