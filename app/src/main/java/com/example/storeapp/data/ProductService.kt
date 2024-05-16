package com.example.storeapp.data

import com.example.storeapp.domain.models.Product
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {
    @GET("products")
    suspend fun getProducts() : List<Product>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id")id:Int) : Product
}