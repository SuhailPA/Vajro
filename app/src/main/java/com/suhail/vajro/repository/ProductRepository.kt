package com.suhail.vajro.repository

import androidx.room.withTransaction
import com.suhail.vajro.api.ProductAPI
import com.suhail.vajro.room.ProductDatabase
import com.suhail.vajro.utils.networkBoundResource
import javax.inject.Inject


class ProductRepository @Inject constructor(
    private val api: ProductAPI,
    private val database : ProductDatabase
) {
    private val productDoa =database.productDao()

    fun allProductItems ()= networkBoundResource(
        query ={
            productDoa.getAllItems()
        },
        fetch = {
            api.readAllItems()
        },
        saveFetchResult = {
            database.withTransaction {
                productDoa.deleteAllData()
                productDoa.insertProductsToProductTable(it.products)
            }
        }, shouldFetch = {
            return@networkBoundResource true
        }
    )

}