package com.example.ecconsol.Roomdb

import android.media.Image
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nonnull

@Entity(tableName = "Products")
data class ProductModel(
    @PrimaryKey
    @Nonnull
    val productId:String,
    @ColumnInfo("productName") val productName: String?="",
    @ColumnInfo("productImage") val productImage: String?="",
    @ColumnInfo("productSP") val productSP: String?=""

)
