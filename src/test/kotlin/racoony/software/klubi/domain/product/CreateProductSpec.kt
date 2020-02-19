package racoony.software.klubi.domain.product

import io.kotlintest.matchers.beOfType
import io.kotlintest.matchers.collections.shouldHaveSize
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec
import racoony.software.klubi.domain.product.ProductType.PHYSICAL
import racoony.software.klubi.event_sourcing.Changes
import java.util.Currency

class CreateProductSpec : BehaviorSpec({
    Given("Product information for a product to be created") {
        val productName = "Basic T-Shirt"
        val description = "A very basic T-Shirt in just one variant."
        val productType = PHYSICAL
        val price = Price(700, Currency.getInstance("EUR"))

        When("Create the product") {
            val product = Product.create(productName, description, productType, price)

            Then("Product created") {
                product.changes shouldHaveSize 1
                product.changes.first() should beOfType(ProductCreated::class)
            }

            Then("Product created contains product name") {
                val event = Changes(product).ofType(ProductCreated::class.java).first()
                event.name shouldBe productName
            }

            Then("Product created contains description") {
                val event = Changes(product).ofType(ProductCreated::class.java).first()
                event.description shouldBe description
            }

            Then("Product created is of type physical") {
                val event = Changes(product).ofType(ProductCreated::class.java).first()
                event.productType shouldBe PHYSICAL
            }

            Then("Product created has a price") {
                val event = Changes(product).ofType(ProductCreated::class.java).first()
                event.price shouldBe Price(700, Currency.getInstance("EUR"))
            }
        }
    }
})