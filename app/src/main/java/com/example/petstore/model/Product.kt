package com.example.petstore.model

import java.lang.StringBuilder

class Product(
    val image: String,
    val name: String,
    val description: String,
    val price: Double,
    val type: Category
) {
    fun CategoryToString(): String {
        val stringBuilder = StringBuilder()

        when (type) {
            Category.CAMAS -> stringBuilder.append("Camas")
            Category.BRINQUEDOS -> stringBuilder.append("Brinquedos")
            Category.COMEDOUROS -> stringBuilder.append("Comedouros")
            Category.CASINHAS -> stringBuilder.append("Casinhas")
        }

        return stringBuilder.toString()
    }

}

enum class Category {
    CAMAS, BRINQUEDOS, COMEDOUROS, CASINHAS;


}
