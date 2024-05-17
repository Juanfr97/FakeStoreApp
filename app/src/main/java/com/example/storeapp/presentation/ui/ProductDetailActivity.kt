package com.example.storeapp.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.storeapp.R
import com.example.storeapp.data.ProductService
import com.example.storeapp.presentation.viewmodels.ProductDetailViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@AndroidEntryPoint
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
        val productDetailViewModel : ProductDetailViewModel by viewModels()
        productName = findViewById(R.id.productName)
        productImage = findViewById(R.id.productImage)
        productDescription = findViewById(R.id.productDescription)
        productPrice = findViewById(R.id.productPrice)
        productRating = findViewById(R.id.productRating)
        productCategory = findViewById(R.id.productCategory)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                productDetailViewModel.productState.collect{state->
                    if(state.isLoading){
                        productName.visibility = TextView.GONE
                        productImage.visibility = ImageView.GONE
                        productDescription.visibility = TextView.GONE
                        productPrice.visibility = TextView.GONE
                        productRating.visibility = TextView.GONE
                        productCategory.visibility = TextView.GONE
                    }
                    else{
                        productName.visibility = TextView.VISIBLE
                        productImage.visibility = ImageView.VISIBLE
                        productDescription.visibility = TextView.VISIBLE
                        productPrice.visibility = TextView.VISIBLE
                        productRating.visibility = TextView.VISIBLE
                        productCategory.visibility = TextView.VISIBLE
                    }
                    if(state.product != null){
                        productName.text = state.product.title
                        Picasso.get().load(state.product.image).into(productImage)
                        productDescription.text = state.product.description
                        productRating.text = state.product.rating.rate.toString()
                        productPrice.text = state.product.computedPrice
                        productCategory.text = state.product.category
                    }
                }
            }
        }
//        val productId = intent.getIntExtra("productId",0)
//        Log.i("ProductIntent",productId.toString())
//        getProduct(productId)
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