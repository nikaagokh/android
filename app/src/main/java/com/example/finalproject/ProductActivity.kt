package com.example.finalproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductActivity: ComponentActivity() {
    private val apiService = RetrofitInstance.apiService
    private lateinit var recyclerView:RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.products)

        val categoryId = intent.getIntExtra("categoryId", -1) // Default value -1 if not found
        val categoryName = intent.getStringExtra("categoryName") ?: "Unknown Category"
        Log.d("prodAct", "s")
        recyclerView = findViewById(R.id.productsRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchProducts(categoryId)

        val backButton = findViewById<Button>(R.id.productBackButton)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Optional: Closes the current activity
        }
    }

    private fun fetchProducts(categoryId:Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.d("salami", "jigaro");
                // Suspend function is called directly within a coroutine
                val products = apiService.getProductsByCategory(categoryId)// This will run asynchronously
                Log.d("products", "categories: $products");
                if (products.isNotEmpty()) {
                    val adapter = ProductsAdapter(products)
                    Log.d("Prodgox", "No categories found")
                    withContext(Dispatchers.Main) {
                        recyclerView.adapter = adapter
                    }
                } else {
                    Log.d("ProdAdapter", "No categories found")
                }
            } catch (e: Exception) {
                Log.e("errorebi", "Posts: posts")
                // Handle the exception on the main thread
                withContext(Dispatchers.Main) {
                    Log.e("MainActivity", "Error fetching posts: ${e.message}")
                }
            }
        }
    }
}