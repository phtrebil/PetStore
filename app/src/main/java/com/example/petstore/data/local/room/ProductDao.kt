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
    fun insert(product: ProductLocal)

    @Delete
    fun delete(vararg products: ProductLocal)

    @Query("SELECT SUM(quantity * price) FROM ProductLocal")
    fun calculateTotalPrice(): Double
}

