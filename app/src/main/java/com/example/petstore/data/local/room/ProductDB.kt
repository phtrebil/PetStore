package com.example.petstore.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.example.petstore.model.ProductLocal

@Database(entities = [ProductLocal::class], version = 1)
abstract class ProductDB: RoomDatabase() {

    companion object{
        @Volatile private var db: ProductDB? = null
        fun instance(context: Context): ProductDB{
            return db ?: databaseBuilder(
                context,
                ProductDB::class.java, "Cliente.db"
            ).build()
                .also {
                    db = it
                }
        }
    }

    abstract fun productDB():ProductDao
}