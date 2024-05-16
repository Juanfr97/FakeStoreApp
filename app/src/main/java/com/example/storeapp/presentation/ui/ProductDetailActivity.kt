package com.example.storeapp.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.storeapp.R
import com.example.storeapp.data.ProductService
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var productName : TextView
    private lateinit var productImage : ImageView
    private lateinit var productDescription : TextView
    private lateinit var productPrice : TextView
    private lateinit var productRating : TextView
    private lateinit var productCategory : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        productName = findViewById(R.id.productName)
        productImage = findViewById(R.id.productImage)
        productDescription = findViewById(R.id.productDescription)
        productPrice = findViewById(R.id.productPrice)
        productRating = findViewById(R.id.productRating)
        productCategory = findViewById(R.id.productCategory)
        val productId = intent.getIntExtra("productId",0)
        Log.i("ProductIntent",productId.toString())
        getProduct(productId)
    }

    private fun getProduct(id:Int){
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductService::class.java)
        lifecycleScope.launch {

            val response = retrofitBuilder.getProductById(id)
            Log.i("ProductResponse",response.toString())
            productName.text = response.title
            Picasso.get().load(response.image).into(productImage)
            productDescription.text = response.description
            productRating.text = response.rating.rate.toString()
            productPrice.text = response.computedPrice
            productCategory.text = response.category
        }
    }
}