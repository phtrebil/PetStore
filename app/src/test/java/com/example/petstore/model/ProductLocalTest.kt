package com.example.petstore.model

import com.example.petstore.model.Category
import com.example.petstore.model.Product
import com.example.petstore.model.ProductLocal
import org.amshove.kluent.shouldBeTrue
import org.junit.Test

class ProductLocalTest {

    val product: Product = Product("imagem", "Bolinha de roer", "Bolinha de cachorro para roer", 19.99, Category.BRINQUEDOS)
    var productLocal: ProductLocal = ProductLocal(0,0, "", "", "", 0.0)

    @Test
    fun `Should return True When the name is the same`(){
        productLocal.productToProductRoom(product)

        val nameProduct: Boolean = productLocal.name == product.name

        nameProduct.shouldBeTrue()

    }

    @Test
    fun `Should return True When the image is the same`(){
        productLocal.productToProductRoom(product)

        val imageProduct: Boolean = productLocal.image == product.image

        imageProduct.shouldBeTrue()

    }

    @Test
    fun `Should return True When the description is the same`(){
        productLocal.productToProductRoom(product)

        val descriptionProduct: Boolean = productLocal.description == product.description

        descriptionProduct.shouldBeTrue()

    }

    @Test
    fun `Should return True When price is the same`(){
        productLocal.productToProductRoom(product)

        val priceProduct: Boolean = productLocal.price == product.price

        priceProduct.shouldBeTrue()

    }
}