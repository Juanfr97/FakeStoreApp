package com.example.storeapp.presentation.states

import com.example.storeapp.domain.models.Product

data class ProductState(
    val isLoading : Boolean = false,
    val product : Product? = null,
    val errorMessage : String = ""
)