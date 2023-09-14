package com.example.petstore.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petstore.data.local.room.ProductDB
import com.example.petstore.data.remote.ProductRepository
import com.example.petstore.model.Product
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeViewModel(
    private val database: ProductDB
) : ViewModel() {
    private val productRepository = ProductRepository()

    private val _productListLiveData = MutableLiveData<List<Product>>()
    val productListLiveData: LiveData<List<Product>> = _productListLiveData

    fun loadProducts() {
        viewModelScope.launch {
            try {
               _productListLiveData.value = productRepository.productService.getProducts()

            } catch (e: Exception) {
                _productListLiveData.value = emptyList()
            }
        }
    }

    suspend fun calculateTotalPrice():Double{
        return viewModelScope.async {
            val totalPrice = database.productDB().calculateTotalPrice()
            totalPrice
        }.await()
    }

    fun clearAllProducts() {
        viewModelScope.launch {
            database.productDB().delete()
        }
    }
}
