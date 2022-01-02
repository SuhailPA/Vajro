package com.suhail.vajro.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.suhail.vajro.data.Cart
import com.suhail.vajro.data.Product

@Database(entities = [Product::class], version = 1)
abstract class ProductDatabase :RoomDatabase() {
    abstract fun productDao() : ProductDao
}