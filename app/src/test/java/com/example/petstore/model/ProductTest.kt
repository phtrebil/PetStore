package com.example.petstore.model

import com.example.petstore.model.Category.BRINQUEDOS
import com.example.petstore.model.Category.CAMAS
import com.example.petstore.model.Category.CASINHAS
import com.example.petstore.model.Category.COMEDOUROS
import org.amshove.kluent.internal.assertEquals
import org.junit.Test

class ProductTest {

    val brinquedos: Product = Product(
        image = "imagem",
        name = "Bolinha de roer",
        description = "Bolinha de cachorro para roer",
        price = 19.99,
        type = BRINQUEDOS
    )
    val camas: Product = Product(
        image = "imagem",
        name = "Bolinha de roer",
        description = "Bolinha de cachorro para roer",
        price = 19.99,
        type = CAMAS
    )
    val comedouros: Product = Product(
        image = "imagem",
        name = "Bolinha de roer",
        description = "Bolinha de cachorro para roer",
        price = 19.99,
        type = COMEDOUROS
    )
    val casinhas: Product = Product(
        image = "imagem",
        name = "Bolinha de roer",
        description = "Bolinha de cachorro para roer",
        price = 19.99,
        type = CASINHAS
    )

    @Test
    fun `Should return true When string is the correct category`(){
        assertEquals("Brinquedos", brinquedos.CategoryToString())
        assertEquals("Camas", camas.CategoryToString())
        assertEquals("Comedouros", comedouros.CategoryToString())
        assertEquals("Casinhas", casinhas.CategoryToString())
    }

}