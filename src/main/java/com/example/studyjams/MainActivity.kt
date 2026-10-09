package com.example.studyjams

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.studyjams.ui.theme.QuizoraTheme

/** Single activity - every screen is a Jetpack Compose function. */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuizoraTheme {
                QuizoraApp()
            }
        }
    }
}
