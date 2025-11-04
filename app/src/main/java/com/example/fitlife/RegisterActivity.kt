package com.example.fitlife

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.example.fitlife.viewmodel.AuthViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var viewModel: AuthViewModel
    private lateinit var etName: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var etConfirmPassword: TextInputEditText
    private lateinit var btnRegister: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnRegister = findViewById(R.id.btnRegister)
        progressBar = findViewById(R.id.progressBar)

        btnRegister.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val confirmPassword = etConfirmPassword.text.toString().trim()

            if (validateInputs(name, email, password, confirmPassword)) {
                performRegister(name, email, password)
            }
        }

        findViewById<TextView>(R.id.tvLogin).setOnClickListener {
            finish() // Vuelve a LoginActivity
        }

        observeRegisterResult()
    }

    private fun validateInputs(name: String, email: String, password: String, confirmPassword: String): Boolean {
        if (name.isEmpty()) {
            etName.error = "Ingresa tu nombre"
            return false
        }
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
        if (password != confirmPassword) {
            etConfirmPassword.error = "Las contraseñas no coinciden"
            return false
        }
        return true
    }

    private fun performRegister(name: String, email: String, password: String) {
        progressBar.visibility = View.VISIBLE
        btnRegister.isEnabled = false
        viewModel.register(name, email, password)
    }

    private fun observeRegisterResult() {
        viewModel.registerResult.observe(this) { result ->
            progressBar.visibility = View.GONE
            btnRegister.isEnabled = true

            result.onSuccess { response ->
                if (response.success) {
                    Toast.makeText(this, "Registro exitoso. Inicia sesión", Toast.LENGTH_SHORT).show()
                    finish() // Vuelve a LoginActivity
                } else {
                    Toast.makeText(this, response.message, Toast.LENGTH_LONG).show()
                }
            }

            result.onFailure { exception ->
                Toast.makeText(this, "Error: ${exception.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}