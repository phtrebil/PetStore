package com.example.petstore.ui.viewmodel

import ProductDatabase
import androidx.lifecycle.ViewModel
import com.example.petstore.data.local.room.ProductDB
import com.example.petstore.model.Product
import com.example.petstore.model.ProductLocal

class DetailViewModel(
    private val database: ProductDB
) : ViewModel() {

    fun addProduct(product: ProductLocal){
        database.productDB().insert(product = product)
    }

}