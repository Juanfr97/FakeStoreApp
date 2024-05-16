package com.example.storeapp.domain.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.storeapp.R
import com.example.storeapp.domain.models.Product
import com.squareup.picasso.Picasso

class ProductsAdapter(
    private val products : List<Product>,
    val onProductClick : (Product) -> Unit
) : RecyclerView.Adapter<ProductsViewholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item,parent,false)
        return ProductsViewholder(view)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ProductsViewholder, position: Int) {
        val product = products[position]
        holder.titleTV.text = product.computedTitle
        holder.price.text = product.computedPrice
        holder.ratingTV.text = product.rating.rate.toString()
        Picasso.get().load(product.image)
            .resize(600,200)
            .centerInside()
            .into(holder.imageIV)
        holder.itemView.setOnClickListener{
            onProductClick(product)
        }
    }


}

class ProductsViewholder(view:View) : RecyclerView.ViewHolder(view){
    val categoryTV : TextView
    val price : TextView
    val imageIV : ImageView
    val titleTV : TextView
    val ratingTV : TextView

    init{
        categoryTV = view.findViewById(R.id.product_category)
        price = view.findViewById(R.id.product_price)
        imageIV = view.findViewById(R.id.product_image)
        titleTV = view.findViewById(R.id.product_name)
        ratingTV = view.findViewById(R.id.product_rating)
    }
}

