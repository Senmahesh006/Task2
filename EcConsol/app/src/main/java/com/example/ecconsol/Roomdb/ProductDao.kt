package com.example.ecconsol.Roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDao {
    @Insert
    suspend  fun inserrtProduct(product:ProductModel)
    @Delete
    suspend fun deleteproduct(product: ProductModel)

    @Query("SELECT*FROM products")
    fun getallproducts():LiveData<List<ProductModel>>

    @Query("SELECT * FROM Products WHERE productId = :id")
    fun isExit(id:String):ProductModel
}