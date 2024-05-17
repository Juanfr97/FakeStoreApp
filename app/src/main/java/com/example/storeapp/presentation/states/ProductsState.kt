package com.example.storeapp.presentation.states

import com.example.storeapp.domain.models.Product

data class ProductsState(
    val isLoading : Boolean = false,
    val products : List<Product> = emptyList(),
    val errorMessage : String = ""
)
