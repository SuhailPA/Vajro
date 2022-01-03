package com.suhail.vajro.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.suhail.vajro.data.Cart
import com.suhail.vajro.data.Product
import kotlinx.coroutines.flow.Flow


@Dao
interface ProductDao {

    @Query("SELECT * FROM productTable")
    fun getAllItems(): Flow<List<Product>>


    @Query("DELETE  FROM productTable")
    suspend fun deleteAllData()

    @Insert
    suspend fun insertProductsToProductTable(products: List<Product>)

    @Query("UPDATE cartTable SET quantitiy = :qty WHERE productId = :Pid")
    suspend fun updateQuantity(qty: Int, Pid: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItemToCart(item: Cart)

    @Query("SELECT quantitiy FROM cartTable WHERE productId LIKE :id")
    suspend fun exists(id: Int): Int?

    @Query("DELETE FROM cartTable WHERE productId LIKE :id")
    suspend fun deleteFromCart(id: Int)

    @Query("SELECT * FROM cartTable")
    fun getCartItems(): Flow<List<Cart>>

}