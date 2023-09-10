package com.example.petstore.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.petstore.model.Product
import com.example.petstore.model.ProductLocal

@Dao
interface ProductDao {

    @Insert
    suspend fun insert(product: ProductLocal)

    @Query("DELETE FROM ProductLocal")
    suspend fun delete()

    @Query("SELECT SUM(quantity * price) FROM ProductLocal")
    suspend fun calculateTotalPrice(): Double
}

