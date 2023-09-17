package com.example.petstore.di

import androidx.room.Room
import com.example.petstore.data.local.room.ProductDB
import com.example.petstore.data.remote.ProductRepository
import com.example.petstore.ui.viewmodel.DetailViewModel
import com.example.petstore.ui.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val databaseModule = module {
    single<ProductDB> {
        Room.databaseBuilder(
            get(),
            ProductDB::class.java,
            "Product.db"
        ).build()
    }
    single<ProductRepository> {
        ProductRepository()
    }

}

val viewModelDi = module {
    viewModelOf(::DetailViewModel)
    viewModelOf(::HomeViewModel)


}





