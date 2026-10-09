package com.example.studyjams

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyjams.ui.theme.BrandPrimary
import com.example.studyjams.ui.theme.TextPrimary
import com.example.studyjams.ui.theme.TextSecondary

/** Lets the player pick one of the quiz levels and shows their best score for each. */
@Composable
fun LevelScreen(
    username: String,
    onStart: (Level) -> Unit,
    onSignOut: () -> Unit
) {
    val context = LocalContext.current
    val name = username.ifBlank { "Guest" }

    GradientScreen {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Hello, $name!",
                        color = Color.White,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Let's begin - pick a level.",
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
                AppLogo(size = 72.dp)
            }

            Spacer(Modifier.height(20.dp))

            for (level in QuizData.levels) {
                LevelCard(
                    level = level,
                    best = Prefs.best(context, level.id),
                    onStart = { onStart(level) }
                )
                Spacer(Modifier.height(14.dp))
            }

            CreditsText(Modifier.fillMaxWidth())
            Spacer(Modifier.height(16.dp))
            OutlinedAppButton(text = "Sign Out", onClick = onSignOut)
        }
    }
}

@Composable
private fun LevelCard(level: Level, best: Int, onStart: () -> Unit) {
    WhiteCard(Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = level.title,
                modifier = Modifier.weight(1f),
                color = BrandPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Badge("${level.questions.size} questions")
        }
        Spacer(Modifier.height(6.dp))
        Text(text = level.description, color = TextSecondary, fontSize = 15.sp)
        Spacer(Modifier.height(6.dp))
        Text(
            text = if (best >= 0) "Best score: $best/${level.questions.size}" else "Not attempted yet",
            color = TextPrimary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(12.dp))
        PrimaryButton(text = "Start", onClick = onStart)
    }
}
