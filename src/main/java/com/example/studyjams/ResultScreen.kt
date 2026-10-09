package com.example.studyjams

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyjams.ui.theme.BrandPrimary
import com.example.studyjams.ui.theme.BrandPrimaryDark
import com.example.studyjams.ui.theme.TextPrimary
import com.example.studyjams.ui.theme.TextSecondary

@Composable
fun ResultScreen(
    level: Level,
    score: Int,
    total: Int,
    newBest: Boolean,
    onTryAgain: () -> Unit,
    onChooseLevel: () -> Unit
) {
    val context = LocalContext.current
    val percent = if (total > 0) score * 100 / total else 0
    val message = when {
        total > 0 && score == total -> "Perfect score!"
        percent >= 60 -> "Great job!"
        percent >= 40 -> "Good effort - keep practising!"
        else -> "Good try - give it another go!"
    }
    val best = Prefs.best(context, level.id)
    val bestText = when {
        newBest -> "New best score!"
        best >= 0 -> "Best score: $best/$total"
        else -> ""
    }

    BackHandler(onBack = onChooseLevel)

    GradientScreen {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AppLogo(size = 96.dp)
            Spacer(Modifier.height(10.dp))
            Text(
                text = "Quiz Completed!",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(20.dp))

            WhiteCard(Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = level.title,
                        color = TextSecondary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Your Score",
                        color = TextPrimary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "$score/$total",
                        color = BrandPrimary,
                        fontSize = 52.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = "$percent%", color = TextSecondary, fontSize = 16.sp)
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = message,
                        color = TextPrimary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic,
                        textAlign = TextAlign.Center
                    )
                    if (bestText.isNotEmpty()) {
                        Spacer(Modifier.height(6.dp))
                        Text(
                            text = bestText,
                            color = BrandPrimaryDark,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(Modifier.height(22.dp))
                    PrimaryButton(text = "Try Again", onClick = onTryAgain)
                    Spacer(Modifier.height(10.dp))
                    OutlinedAppButton(text = "Choose Another Level", onClick = onChooseLevel)
                }
            }
        }
    }
}
