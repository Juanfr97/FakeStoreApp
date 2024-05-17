package com.example.storeapp.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storeapp.domain.use_cases.GetProductsById
import com.example.storeapp.presentation.ApiResult
import com.example.storeapp.presentation.states.ProductState
import com.example.storeapp.presentation.states.ProductsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor (
    savedStateHandle: SavedStateHandle,
    private val getProductUseCase : GetProductsById
):ViewModel()
{
    private val productId = savedStateHandle.get<Int>("productId") ?: 0
    private var productJob : Job? = null
    private var _productState = MutableStateFlow(ProductState())
    val productState = _productState.asStateFlow()

    init{
        getProduct()
    }

    private fun getProduct(){
        productJob?.cancel()
        productJob = getProductUseCase(productId).onEach { result ->
            when(result){
                is ApiResult.Loading -> {
                    _productState.value = ProductState(isLoading = true)
                }
                is ApiResult.Success -> {
                    _productState.value = ProductState(product = result.data, isLoading = false)
                }
                is ApiResult.Error -> {
                    _productState.value = ProductState(errorMessage = result.message, isLoading = false)
                }
            }
        }.launchIn(viewModelScope)
    }
}