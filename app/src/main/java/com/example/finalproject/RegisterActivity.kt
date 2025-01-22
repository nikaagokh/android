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


class RegisterActivity:ComponentActivity() {
    private val apiService = RetrofitInstance.apiService

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
        val firstNameInput = findViewById<EditText>(R.id.firstName);
        val lastNameInput = findViewById<EditText>(R.id.lastName);
        val emailInput = findViewById<EditText>(R.id.email);
        val passwordInput = findViewById<EditText>(R.id.password);

        val registerButton = findViewById<Button>(R.id.registerButton)
        registerButton.setOnClickListener {
            val firstName = firstNameInput.text.toString()
            val lastName = lastNameInput.text.toString()
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()
            if(firstName.isEmpty()) {
                Toast.makeText(this, "გთხოვთ შეიყვანოთ სახელი", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(lastName.isEmpty()) {
                Toast.makeText(this, "გთხოვთ შეიყვანოთ გვარი", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(email.isEmpty()) {
                Toast.makeText(this, "გთხოვთ შეიყვანოთ ემაილი", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(password.isEmpty()) {
                Toast.makeText(this, "გთხოვთ შეიყვანოთ პაროლი", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(password.length < 8) {
                Toast.makeText(this, "აუცილებელია, რომ პაროლი იყოს მინიმუმ 8 სიმბოლოიანი", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = RegisterRequest(
                firstName = firstName,
                lastName = lastName,
                email = email,
                password = password
            )

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = apiService.registerUser(user)

                    Log.d("registered", "Reg: $response");
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@RegisterActivity, "თქვენ წარმატებით გაიარეთ რეგისტრაცია", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                } catch (e: Exception) {
                    // Handle errors on the main thread
                    Log.d("err", " ${e.message}")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@RegisterActivity, "რეგისტრაცია ვერ შესრულდა. სცადეთ ისევ", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}