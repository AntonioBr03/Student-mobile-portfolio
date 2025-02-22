package com.example.aut_portal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aut_portal.Entity.LoginRequest
import com.example.aut_portal.Entity.UserEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var buttonLogin: Button
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var auth: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        buttonLogin = findViewById(R.id.login)
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        auth = findViewById(R.id.Authentication)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        buttonLogin.setOnClickListener {
            val user = username.text.toString()
            val pass = password.text.toString()

            if (user.isNotEmpty() && pass.isNotEmpty()) {
                authenticateUser(user, pass)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun authenticateUser(username: String, password: String) {
        val loginRequest = LoginRequest(
            Std_uname = username,
            Std_Pass = password
        )

        RetrofitClient.authenticateUser(loginRequest) { loginResponse ->
            if (loginResponse != null && loginResponse.status == "success") {
                val userId = loginResponse.user_id
                Log.d("MainActivity", "User ID: $userId")
                Log.d("MainActivity", "Login Response: $loginResponse")
                Log.d("MainActivity", "Username: $username, Password: $password")


                // Check if the userId is valid
                if (userId != null && userId != 0) {
                    val intent = Intent(this@MainActivity, HomePage::class.java).apply {
                        putExtra("Std_ID", userId)
                    }
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@MainActivity, "Invalid User ID", Toast.LENGTH_SHORT).show()
                }
            } else {
                Log.e("LoginError", "Login Response: ${loginResponse?.message ?: "Error occurred"}")
                Toast.makeText(
                    this@MainActivity,
                    loginResponse?.message ?: "Error occurred",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

}






