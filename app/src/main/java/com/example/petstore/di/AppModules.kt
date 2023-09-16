package com.example.petstore.di

import androidx.room.Room
import com.example.petstore.data.local.room.ProductDB
import org.koin.dsl.module


val databaseModule = module {
    single<ProductDB> {
        Room.databaseBuilder(
            get(),
            ProductDB::class.java,
            "Product.db"
        ).build()
    }
}