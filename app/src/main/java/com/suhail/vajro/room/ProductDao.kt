package com.suhail.vajro.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.suhail.vajro.data.Product
import kotlinx.coroutines.flow.Flow


@Dao
interface ProductDao {

    @Query("SELECT * FROM productTable")
    fun getAllItems (): Flow<List<Product>>

    @Query("DELETE  FROM productTable")
    suspend fun deleteAllData()

    @Insert
    suspend fun insertProductsToProductTable(products:List<Product>)

}