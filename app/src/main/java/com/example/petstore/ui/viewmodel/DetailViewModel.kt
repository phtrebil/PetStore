package com.example.petstore.ui.viewmodel

import ProductDatabase
import androidx.lifecycle.ViewModel
import com.example.petstore.model.ProductLocal

class DetailViewModel(
    private val database: ProductDatabase
) : ViewModel() {

    fun addProduct(product: ProductLocal){
        database.addProduct(product)
    }

}