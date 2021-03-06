package com.suhail.vajro.data


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "productTable")
data class Product(
    @SerializedName("description")
    val description: String,
    @SerializedName("href")
    val href: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("product_id")
    @PrimaryKey(autoGenerate = false)
    val productId: String,
    @SerializedName("quantity")
    var quantity: Int,
    @SerializedName("sku")
    val sku: String,
    @SerializedName("special")
    val special: String,
    @SerializedName("thumb")
    val thumb: String,
    @SerializedName("zoom_thumb")
    val zoomThumb: String
) :Parcelable