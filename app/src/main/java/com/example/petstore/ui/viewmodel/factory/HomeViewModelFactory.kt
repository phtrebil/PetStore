package com.example.petstore.ui.viewmodel.factory

import ProductDatabase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.petstore.data.local.room.ProductDB
import com.example.petstore.ui.viewmodel.HomeViewModel

class HomeViewModelFactory(private val database: ProductDB) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
