package com.example.c38re.model

data class ProductModel(
    var productId : String = "",
    var productName : String = "",
    var price : Double = 0.0,
    var description : String = "",
    var isActive : Boolean = false,
    var quantity : Int = 0
){
    fun toMap() : Map<String,Any?>{
        return mapOf(
            "productName" to productName,
            "price" to price,
            "description" to description,
            "isActive" to isActive,
            "quantity" to quantity,
        )
    }
}
