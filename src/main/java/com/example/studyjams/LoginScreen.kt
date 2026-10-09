package com.example.studyjams

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyjams.ui.theme.BrandPrimary
import com.example.studyjams.ui.theme.BrandSoft
import com.example.studyjams.ui.theme.TextPrimary
import com.example.studyjams.ui.theme.TextSecondary
import com.example.studyjams.ui.theme.WrongRed

private const val DEFAULT_PASSWORD = "Mosaic@05!"

@Composable
fun LoginScreen(onSignedIn: (String) -> Unit) {
    val context = LocalContext.current

    var username by rememberSaveable { mutableStateOf(Prefs.username(context)) }
    var password by rememberSaveable { mutableStateOf("") }
    var showPassword by rememberSaveable { mutableStateOf(false) }
    var usernameError by rememberSaveable { mutableStateOf<String?>(null) }
    var passwordError by rememberSaveable { mutableStateOf<String?>(null) }

    fun signIn() {
        val name = username.trim()
        usernameError = null
        passwordError = null
        if (name.isEmpty()) {
            usernameError = "Please enter a username"
            return
        }
        if (password != DEFAULT_PASSWORD) {
            passwordError = "Invalid password"
            Toast.makeText(context, "Login failed: invalid credentials", Toast.LENGTH_SHORT).show()
            return
        }
        Prefs.setUsername(context, name)
        Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
        onSignedIn(name)
    }

    val userErrorText = usernameError
    val passErrorText = passwordError

    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = BrandPrimary,
        unfocusedBorderColor = BrandSoft,
        focusedLabelColor = BrandPrimary,
        cursorColor = BrandPrimary,
        focusedTextColor = TextPrimary,
        unfocusedTextColor = TextPrimary,
        errorBorderColor = WrongRed
    )

    GradientScreen {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AppLogo(size = 110.dp)
            Spacer(Modifier.height(12.dp))
            Text(
                text = "Welcome to Quizora",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(24.dp))

            WhiteCard(Modifier.fillMaxWidth()) {
                Text(
                    text = "Sign In",
                    color = TextPrimary,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Enter a username and the default password to take part in the quiz.",
                    color = TextSecondary,
                    fontSize = 14.sp
                )
                Spacer(Modifier.height(20.dp))

                OutlinedTextField(
                    value = username,
                    onValueChange = {
                        username = it
                        usernameError = null
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Username") },
                    singleLine = true,
                    isError = usernameError != null,
                    supportingText = if (userErrorText != null) {
                        { Text(userErrorText) }
                    } else null,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    shape = RoundedCornerShape(16.dp),
                    colors = fieldColors
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        passwordError = null
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Password") },
                    singleLine = true,
                    isError = passwordError != null,
                    supportingText = if (passErrorText != null) {
                        { Text(passErrorText) }
                    } else null,
                    visualTransformation = if (showPassword) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    trailingIcon = {
                        TextButton(onClick = { showPassword = !showPassword }) {
                            Text(if (showPassword) "Hide" else "Show", color = BrandPrimary)
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = { signIn() }),
                    shape = RoundedCornerShape(16.dp),
                    colors = fieldColors
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Default password: $DEFAULT_PASSWORD",
                    color = TextSecondary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(20.dp))
                PrimaryButton(text = "Sign In", onClick = { signIn() })
            }

            Spacer(Modifier.height(16.dp))
            CreditsText()
        }
    }
}
