package com.example.petstore

import com.example.petstore.model.Category
import com.example.petstore.model.Product
import com.example.petstore.model.ProductLocal
import org.amshove.kluent.shouldBeTrue
import org.junit.Test
import java.math.BigDecimal

class ProductLocalTest {

    val product: Product = Product("imagem", "Bolinha de roer", "Bolinha de cachorro para roer", 19.99, Category.BRINQUEDOS)
    var productLocal: ProductLocal = ProductLocal(0,0, "", "", "", 0.0)

    @Test
    fun `Should return True When the name is the same`(){
        productLocal.productToProductRoom(product)

        val nomeProduto: Boolean = productLocal.name == product.name

        nomeProduto.shouldBeTrue()

    }
}