package com.example.fitlife

import androidx.appcompat.app.AppCompatActivity
import com.example.fitlife.viewmodel.AuthViewModel
import com.google.android.material.textfield.TextInputEditText
import android.widget.Button
import android.widget.ProgressBar
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import android.widget.TextView
import android.content.Intent
import android.view.View
import kotlin.jvm.java
import android.content.Context
import com.example.fitlife.utils.TokenManager
import android.widget.Toast


class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: AuthViewModel
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnLogin: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        progressBar = findViewById(R.id.progressBar)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (validateInputs(email, password)) {
                performLogin(email, password)
            }
        }

        findViewById<TextView>(R.id.tvRegister).setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        observeLoginResult()
    }

    private fun validateInputs(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            etEmail.error = "Ingresa tu email"
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = "Email inválido"
            return false
        }
        if (password.isEmpty()) {
            etPassword.error = "Ingresa tu contraseña"
            return false
        }
        if (password.length < 6) {
            etPassword.error = "La contraseña debe tener al menos 6 caracteres"
            return false
        }
        return true
    }

    private fun performLogin(email: String, password: String) {
        progressBar.visibility = View.VISIBLE
        btnLogin.isEnabled = false
        viewModel.login(email, password)
    }

    private fun observeLoginResult() {
        viewModel.loginResult.observe(this) { result ->
            progressBar.visibility = View.GONE
            btnLogin.isEnabled = true

            result.onSuccess { response ->
                if (response.success) {

                    saveToken(response.token)

                    Toast.makeText(this, "Bienvenido ${response.user?.name}", Toast.LENGTH_SHORT).show()

                    // Ir a MainActivity
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, response.message, Toast.LENGTH_LONG).show()
                }
            }

            result.onFailure { exception ->
                Toast.makeText(this, "Error: ${exception.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun saveToken(token: String?) {
        token?.let {
            TokenManager.saveToken(this, it)
        }
    }
}