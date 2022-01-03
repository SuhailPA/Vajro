package com.suhail.vajro.api

import com.suhail.vajro.data.ProductResponce
import retrofit2.http.GET

interface ProductAPI {
    companion object {
        const val BASE_URL = " https://www.mocky.io/v2/5def7b172f000063008e0aa2/"
    }

    @GET(".")
    suspend fun readAllItems(): ProductResponce
}