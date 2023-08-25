package com.example.petstore.model


class ProductLocal(
    val id: Int = 0,
    var quantity: Int,
    var image: String,
    var name: String,
    var description: String,
    var price: Double
) {

    fun productToProductRoom(product: Product) {
        this.image = product.image
        this.name = product.name
        this.description = product.description
        this.price = product.price
    }
}
