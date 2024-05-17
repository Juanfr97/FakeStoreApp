package com.example.storeapp.di

import com.example.storeapp.data.ProductService
import com.example.storeapp.domain.use_cases.GetProducts
import com.example.storeapp.domain.use_cases.GetProductsById
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private val BASE_URL = "https://fakestoreapi.com/"

    @Provides
    @Singleton
    fun provideProductService() : ProductService{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductService::class.java)
    }

    @Provides
    @Singleton
    fun provideGetProductsUseCase(productService:ProductService) : GetProducts{
        return GetProducts(productService)
    }

    @Provides
    @Singleton
    fun provideGetProductsByIdUseCase(productService : ProductService) : GetProductsById{
        return GetProductsById(productService)
    }
}