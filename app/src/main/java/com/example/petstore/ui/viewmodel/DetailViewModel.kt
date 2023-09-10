package com.example.petstore.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petstore.data.local.room.ProductDB
import com.example.petstore.model.ProductLocal
import kotlinx.coroutines.launch

class DetailViewModel(
    private val database: ProductDB
) : ViewModel() {

    fun addProduct(product: ProductLocal){
        viewModelScope.launch {
            database.productDB().insert(product = product)
        }
    }

}