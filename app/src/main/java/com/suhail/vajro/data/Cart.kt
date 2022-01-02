package com.suhail.vajro.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cartTable")
data class Cart(
    var imageUrl :String,
    var name : String,
    var price : String,
    var quantitiy : Int,
    @PrimaryKey(autoGenerate = false)
    var productId : String
)
