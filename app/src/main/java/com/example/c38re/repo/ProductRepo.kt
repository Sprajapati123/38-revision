package com.example.c38re.repo

import com.example.c38re.model.ProductModel

interface ProductRepo {

    //    {
//        "success": true,
//        "message": "Product added succesfully"
//    }
    fun addProduct(
        model: ProductModel,
        callback: (Boolean, String) -> Unit
    )

    fun updateProduct(
        model: ProductModel,
        callback: (Boolean, String) -> Unit
    )

    fun deleteProduct(
        id: String,
        callback: (Boolean, String) -> Unit
    )

    fun getProductById(
        id: String,
        callback: (Boolean, ProductModel?) -> Unit
    )

    fun getAllProduct(
        callback: (Boolean, List<ProductModel>?) -> Unit)

    fun filterProduct(isActive: Boolean,callback: (Boolean, List<ProductModel>?) -> Unit)
    fun searchProduct(name: String,callback: (Boolean, List<ProductModel>?) -> Unit)
    fun getProductByCategory(categoryID: String,callback: (Boolean, List<ProductModel>?) -> Unit)
}