package com.example.finalproject

data class Product(
    val product_id: Int,
    val title: String,
    val price: Int,
    val description: String,
    val category_name: String,
    val condition_name: String,
    val area: Int,
    val clause_type: String,
    val location_name: String,
    val image_path: String,
    val category_id: Int
)