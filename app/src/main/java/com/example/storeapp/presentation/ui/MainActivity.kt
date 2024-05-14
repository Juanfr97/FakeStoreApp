package com.example.storeapp.presentation.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.storeapp.R
import com.example.storeapp.data.ProductService
import com.example.storeapp.domain.adapters.ProductsAdapter
import com.example.storeapp.domain.models.Product
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private var productList = emptyList<Product>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recycleView)
        progressBar = findViewById(R.id.progress_bar)

        val layoutManager = GridLayoutManager(this,2)
        recyclerView.layoutManager = layoutManager
        getProducts()
    }
    //KISS Keep It Simple Stupid
    private fun getProducts(){
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductService::class.java)

        lifecycleScope.launch {
            try{
                progressBar.visibility = ProgressBar.VISIBLE
                val response = retrofitBuilder.getProducts()
                Log.i("ProductResponse",response.toString())
                productList = response
                progressBar.visibility = ProgressBar.GONE
                recyclerView.adapter = ProductsAdapter(productList){
                    val intent = Intent(this@MainActivity,ProductDetailActivity::class.java)
                    intent.putExtra("productId",it.id)
                    startActivity(intent)
                }
            }
            catch (e: Exception){
                progressBar.visibility = ProgressBar.GONE
                Log.e("ProductResponse",e.toString())
            }

        }
    }

}