package com.example.petstore.ui.viewmodel

import ProductDatabase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petstore.data.remote.ProductRepository
import com.example.petstore.model.Product
import kotlinx.coroutines.launch

class HomeViewModel(
    private val database: ProductDatabase
) : ViewModel() {
    private val productRepository = ProductRepository()

    private val _productListLiveData = MutableLiveData<List<Product>>()
    val productListLiveData: LiveData<List<Product>> = _productListLiveData

    fun loadProducts() {
        viewModelScope.launch {
            try {
                val productList = productRepository.productService.getProducts()
                _productListLiveData.value = productList

            } catch (e: Exception) {

                _productListLiveData.value = emptyList()
            }
        }
    }

    fun calculateTotalPrice(): Double {
        return database.calculateTotalPrice()
    }

    fun clearAllProducts() {
        database.clearAllProducts()
    }
}
