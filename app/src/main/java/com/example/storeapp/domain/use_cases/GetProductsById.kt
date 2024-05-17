package com.example.storeapp.domain.use_cases

import com.example.storeapp.data.ProductService
import com.example.storeapp.domain.models.Product
import com.example.storeapp.domain.models.Rating
import com.example.storeapp.presentation.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetProductsById(
    private val productsService : ProductService
) {
    operator fun invoke(id : Int) : Flow<ApiResult<Product>> = flow {
        try {
            emit(ApiResult.Loading("Cargando"))
            val response = productsService.getProductById(id)
            emit(ApiResult.Success(data = response))
        }
        catch (e : Exception){
            emit(ApiResult.Error(message = "Error", data = null))
        }
    }
}