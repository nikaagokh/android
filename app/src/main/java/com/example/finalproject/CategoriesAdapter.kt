package com.example.finalproject
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class CategoriesAdapter(private val categories:List<Category>):RecyclerView.Adapter<CategoriesAdapter.ViewHolderClass>() {
    class ViewHolderClass(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.categoryName);
        val imageView: ImageView = itemView.findViewById(R.id.categoryImage);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return ViewHolderClass(itemView)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = categories[position]
        Log.d("cata", "Binding item at position $position: ${currentItem.category_name}")
        holder.textView.text = currentItem.category_name
        Log.d("cada", "Number of categories: ${categories.size}")
        val imageUrl = "https://android-app-drab.vercel.app/public/images/categories/${currentItem.category_image_path}"
        Log.d("CategoriesAdapter", "Image URL: $imageUrl")

        Picasso.get()
            .load(imageUrl)
            .into(holder.imageView)


        holder.itemView.setOnClickListener {
            Log.d("clicked", "salami")
            val context = holder.itemView.context
            val intent = Intent(context, ProductActivity::class.java) // Replace with your actual activity name
            intent.putExtra("categoryId", currentItem.category_id) // Pass category_id to the new activity
            intent.putExtra("categoryName", currentItem.category_name) // Optional: Pass category_name
            context.startActivity(intent)
        }
    }
}