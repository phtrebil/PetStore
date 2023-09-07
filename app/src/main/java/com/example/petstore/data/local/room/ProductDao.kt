package com.example.petstore.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.example.petstore.model.Product

@Dao
interface ProductDao {

    @Insert
    fun insert(product: Product)

    @Delete
    fun delete(vararg products: Product)

}