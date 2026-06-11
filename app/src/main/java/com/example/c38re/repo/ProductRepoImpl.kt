package com.example.c38re.repo

import com.example.c38re.model.ProductModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProductRepoImpl : ProductRepo {
    val database = FirebaseDatabase.getInstance()
    val ref = database.getReference("products")

    override fun addProduct(
        model: ProductModel,
        callback: (Boolean, String) -> Unit
    ) {
        val id = ref.push().key.toString()
        model.productId = id

        ref.child(id).setValue(model).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Product added succesfully")
            } else {
                callback(false, "${it.exception?.message}")
            }
        }
    }

    override fun updateProduct(
        model: ProductModel,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(model.productId)
            .updateChildren(model.toMap()).addOnCompleteListener {
                if (it.isSuccessful) {
                    callback(true, "Product updated succesfully")
                } else {
                    callback(false, "${it.exception?.message}")
                }
            }
    }

    override fun deleteProduct(
        id: String,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(id).removeValue().addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Product deleted succesfully")
            } else {
                callback(false, "${it.exception?.message}")
            }
        }
    }

    override fun getProductById(
        id: String,
        callback: (Boolean, ProductModel?) -> Unit
    ) {
        ref.child(id).addValueEventListener(
            object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        val products = snapshot.getValue(ProductModel::class.java)
                        products.let {
                            callback(true,it)
                        }
                    }
                }
                override fun onCancelled(p0: DatabaseError) {
                    callback(false,null)
                }
            }
        )
    }

    override fun getAllProduct(callback: (Boolean, List<ProductModel>?) -> Unit) {
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var allProducts = mutableListOf<ProductModel>()
                    for(data in snapshot.children){
                        val product = data.getValue(ProductModel::class.java)
                        product?.let {
                            allProducts.add(it)
                        }
                    }
                    callback(true,allProducts)
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                callback(false,emptyList())
            }
        })
    }

    override fun filterProduct(
        isActive: Boolean,
        callback: (Boolean, List<ProductModel>?) -> Unit
    ) {
        ref.orderByChild("isActive").equalTo(isActive).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun searchProduct(
        name: String,
        callback: (Boolean, List<ProductModel>?) -> Unit
    ) {
        ref.orderByChild("productName").startAt(name).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun getProductByCategory(
        categoryID: String,
        callback: (Boolean, List<ProductModel>?) -> Unit
    ) {
        ref.orderByChild("categoryId").equalTo(categoryID).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}