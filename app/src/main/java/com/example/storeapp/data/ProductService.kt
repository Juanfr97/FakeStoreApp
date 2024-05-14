package com.example.storeapp.data

import com.example.storeapp.domain.models.Product
import retrofit2.http.GET

interface ProductService {
    @GET("products")
    suspend fun getProducts() : List<Product>
}