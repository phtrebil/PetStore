package com.example.petstore.ui.viewmodel.factory

import ProductDatabase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.petstore.ui.viewmodel.DetailViewModel

class DetailViewModelFactory(private val database: ProductDatabase): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}