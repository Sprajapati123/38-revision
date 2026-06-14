package com.example.c38re.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.c38re.model.ProductModel
import com.example.c38re.repo.ProductRepo

class ProductViewModel(val repo: ProductRepo) : ViewModel(){
    fun addProduct(
        model: ProductModel,
        callback: (Boolean, String) -> Unit
    ){
        repo.addProduct(model,callback)
    }

    fun updateProduct(
        model: ProductModel,
        callback: (Boolean, String) -> Unit
    ){
        repo.updateProduct(model,callback)
    }

    fun deleteProduct(
        id: String,
        callback: (Boolean, String) -> Unit
    ){
        repo.deleteProduct(id,callback)
    }

    //getProductById
    private val _products = MutableLiveData<ProductModel?>()
    val products : MutableLiveData<ProductModel?> get() = _products

    //getProductByCategory
    private val _productByCategory = MutableLiveData<List<ProductModel>?>()
    val productByCategory : MutableLiveData<List<ProductModel>?> get() = _productByCategory

    //getAllProduct
    private val _allProducts = MutableLiveData<List<ProductModel>?>()
    val allProducts : MutableLiveData<List<ProductModel>?> get() = _allProducts

    //filterProduct
    private val _filterProducts = MutableLiveData<List<ProductModel>?>()
    val filterProducts : MutableLiveData<List<ProductModel>?> get() = _filterProducts


    //searchProduct
    private val _searchProducts = MutableLiveData<List<ProductModel>?>()
    val searchProducts : MutableLiveData<List<ProductModel>?> get() = _searchProducts

    //loading
    private val _loading = MutableLiveData<Boolean>()
    val loading : MutableLiveData<Boolean> get() = _loading


    fun getProductById(
        id: String,
    ){
        _loading.value = true
        repo.getProductById(id){
            success,data->
            if(success){
               _products.value = data
                _loading.value = false
            }else{
                _products.value = null
                _loading.value = false
            }
        }
    }

    fun getAllProduct(){

    }

    fun filterProduct(isActive: Boolean){

    }
    fun searchProduct(name: String){

    }
    fun getProductByCategory(categoryID: String,){

    }
}