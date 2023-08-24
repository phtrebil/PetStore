package com.example.petstore.model

class Product(
    val image: String,
    val name: String,
    val description: String,
    val price: Double,
    val Type: Category
) {

}

enum class Category {
    CAMAS, BRINQUEDOS, COMEDOUROS, CASINHAS
}
