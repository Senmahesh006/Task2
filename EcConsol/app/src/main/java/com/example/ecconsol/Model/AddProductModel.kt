package com.example.ecconsol.Model

data class AddProductModel(
    var ProductName:String?="",
    var ProductDescripton:String?="",
    var ProductCoverImg:String?="",
    var ProductCategory:String?="",
    var ProductId:String?="",
    var ProductMRP:String?="",
    var ProductSP:String?="",
    var ProductImage:ArrayList<String> = ArrayList()

)
