package com.example.petstore.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.petstore.data.local.room.ProductDB
import com.example.petstore.ui.viewmodel.DetailViewModel

class DetailViewModelFactory(private val database: ProductDB): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}