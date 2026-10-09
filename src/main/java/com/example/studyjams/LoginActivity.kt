package com.example.studyjams

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class LoginActivity : AppCompatActivity() {

    private lateinit var usernameInput: EditText
    private lateinit var passwordInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        applyInsets(findViewById(R.id.loginRoot))

        usernameInput = findViewById(R.id.usernameInput)
        passwordInput = findViewById(R.id.passwordInput)

        usernameInput.setText(Prefs.username(this))

        findViewById<MaterialButton>(R.id.signInButton).setOnClickListener { signIn() }
        passwordInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                signIn()
                true
            } else {
                false
            }
        }
    }

    private fun signIn() {
        val username = usernameInput.text.toString().trim()
        val password = passwordInput.text.toString()

        if (username.isEmpty()) {
            usernameInput.error = "Please enter a username"
            usernameInput.requestFocus()
            return
        }
        if (password != DEFAULT_PASSWORD) {
            passwordInput.error = "Invalid password"
            passwordInput.requestFocus()
            Toast.makeText(this, "Login failed: invalid credentials", Toast.LENGTH_SHORT).show()
            return
        }

        Prefs.setUsername(this, username)
        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, LevelActivity::class.java))
        finish()
    }

    companion object {
        private const val DEFAULT_PASSWORD = "Mosaic@05!"
    }
}
