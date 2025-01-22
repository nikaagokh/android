package com.example.finalproject

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class ProductsAdapter(private val products:List<Product>):RecyclerView.Adapter<ProductsAdapter.ViewHolderClass>() {
    class ViewHolderClass(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.productName);
        val imageView: ImageView = itemView.findViewById(R.id.productImage);
        val priceView: TextView = itemView.findViewById(R.id.productPrice);
        val locationView: TextView = itemView.findViewById(R.id.productLocation);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ViewHolderClass(itemView)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = products[position]

        holder.textView.text = "ფართობი: ${currentItem.area} კვ/კმ";
        holder.priceView.text = "ფასი : ${currentItem.price} ₾";
        holder.locationView.text = "ადგილმდებარეობა: ${currentItem.location_name}";
        Log.d("product-size", "Number of categories: ${products.size}")
        val imageUrl = "https://android-app-drab.vercel.app/public/images/product/${currentItem.image_path}"
        Log.d("ProductsAdapter", "Image URL: $imageUrl")

        Picasso.get()
            .load(imageUrl)
            .into(holder.imageView)
    }
}