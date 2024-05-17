package com.example.storeapp.domain.use_cases

import com.example.storeapp.data.ProductService
import com.example.storeapp.domain.models.Product
import com.example.storeapp.presentation.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class GetProducts(
    private val productService: ProductService
) {
    operator fun invoke() : Flow<ApiResult<List<Product>>> = flow {
        try{
            emit(ApiResult.Loading("Cargando"))
            val response = productService.getProducts()
            emit(ApiResult.Success(response))
        }
        catch (e:Exception){
            emit(ApiResult.Error(message = "La peticion fallo", data = emptyList()))
        }
    }
}