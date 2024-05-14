package com.example.storeapp.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.storeapp.R
import com.example.storeapp.data.ProductService
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var productName: TextView
    private lateinit var productDescription: TextView
    private lateinit var productPrice: TextView
    private lateinit var productCategory: TextView
    private lateinit var productImage: ImageView
    private lateinit var productRating: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        val productId = intent.getIntExtra("productId",0)
        productName = findViewById(R.id.productName)
        productDescription = findViewById(R.id.productDescription)
        productPrice = findViewById(R.id.productPrice)
        productCategory = findViewById(R.id.productCategory)
        productImage = findViewById(R.id.productImage)
        productRating = findViewById(R.id.product_detail_rating)
        progressBar = findViewById(R.id.progressBarProductDetail)
        getProduct(productId)
    }

    private fun getProduct(productId: Int){
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductService::class.java)

        lifecycleScope.launch {
            try{
                progressBar.visibility = ProgressBar.VISIBLE
                val response = retrofitBuilder.getProductById(productId)
                Log.i("ProductResponse",response.toString())
                productName.text = response.title
                productDescription.text = response.description
                productPrice.text = response.computedPrice
                productCategory.text = response.category
                productRating.text = response.rating.rate.toString()
                progressBar.visibility = ProgressBar.GONE
                Picasso.get().load(response.image).into(productImage)
            }
            catch (e: Exception){
                progressBar.visibility = ProgressBar.GONE
                Log.e("ProductResponse",e.toString())
            }
        }
    }
}