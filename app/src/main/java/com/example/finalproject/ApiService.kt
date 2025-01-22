package com.example.finalproject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiService {

    @GET("product/{categoryId}")
    suspend fun getProductsByCategory(@Path("categoryId") categoryId: Int): List<Product>

    @GET("category/full")
    suspend fun getCategories(): List<Category>

    @POST("auth/register")
    suspend fun registerUser(@Body request: RegisterRequest):ApiResponse

    @POST("auth/login")
    suspend fun loginUser(@Body request: LoginRequest):ApiResponse

}