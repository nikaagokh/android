package com.example.finalproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    private val apiService = RetrofitInstance.apiService
    private lateinit var categoriesRecyclerView:RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(3000)
        installSplashScreen()
        setContentView(R.layout.activity_main) // Link to your XML layout


        categoriesRecyclerView = findViewById(R.id.categoriesRecycler)
        categoriesRecyclerView.layoutManager = LinearLayoutManager(this)
        fetchCategories()

        val regButton = findViewById<Button>(R.id.registerButton)
        regButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()         }

        val authButton = findViewById<Button>(R.id.authButton)
        authButton.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun fetchCategories() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val categories = apiService.getCategories() // This will run asynchronously

                if (categories.isNotEmpty()) {
                    val adapter = CategoriesAdapter(categories)
                    withContext(Dispatchers.Main) {
                        categoriesRecyclerView.adapter = adapter
                    }
                } else {
                    Log.d("CategoriesAdapter", "No categories found")
                }
            } catch (e: Exception) {
                Log.e("err", "MainAct:${e.message}")

            }
        }
    }
}
