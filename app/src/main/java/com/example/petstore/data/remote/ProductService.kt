package com.example.petstore.data.remote

import com.example.petstore.model.Product
import retrofit2.http.GET

interface ProductService {
    @GET("PetStoreAPI.json")
    suspend fun getProducts(): List<Product>
}