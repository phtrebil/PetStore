package com.example.petstore.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductRepository {

    val response = Retrofit.Builder()
        .baseUrl("https://phtrebil.github.io/PetStoreApi/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val productService = response.create(ProductService::class.java)
}