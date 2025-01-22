package com.example.finalproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AuthActivity:ComponentActivity() {
    private val apiService = RetrofitInstance.apiService

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val emailInput = findViewById<EditText>(R.id.email);
        val passwordInput = findViewById<EditText>(R.id.password);

        val authButton = findViewById<Button>(R.id.loginButton)
        authButton.setOnClickListener {

            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            if(email.isEmpty()) {
                Toast.makeText(this, "გთხოვთ შეიყვანოთ ემაილი", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(password.isEmpty()) {
                Toast.makeText(this, "გთხოვთ შეიყვანოთ პაროლი", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val user = LoginRequest(
                email = email,
                password = password
            )

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = apiService.loginUser(user)

                    Log.d("authed", "Post: $response");
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@AuthActivity, "თქვენ წარმატებით გაიარეთ აუტორიზაცია", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@AuthActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                } catch (e: Exception) {
                    // Handle errors on the main thread
                    Log.d("err", "ss ${e.message}")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@AuthActivity, "მომხმარებლის ემაილი ან პაროლი არასწორია", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }


    }
}