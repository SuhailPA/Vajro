package com.suhail.vajro.data


import com.google.gson.annotations.SerializedName

data class ProductResponce(
    @SerializedName("products")
    val products: List<Product>
)