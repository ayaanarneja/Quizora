package com.example.studyjams

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyjams.ui.theme.BrandPrimary
import com.example.studyjams.ui.theme.BrandSoft
import com.example.studyjams.ui.theme.CorrectGreen
import com.example.studyjams.ui.theme.CorrectGreenSoft
import com.example.studyjams.ui.theme.OptionBackground
import com.example.studyjams.ui.theme.TextPrimary
import com.example.studyjams.ui.theme.WrongRed
import com.example.studyjams.ui.theme.WrongRedSoft
import kotlin.random.Random

/**
 * Runs one quiz level. Per question: pick an option -> Submit (answer revealed in green/red)
 * -> Next / Finish. Options are shuffled per attempt ([seed]); progress survives rotation.
 */
@Composable
fun QuizScreen(
    level: Level,
    seed: Long,
    onFinished: (score: Int) -> Unit,
    onQuit: () -> Unit
) {
    val context = LocalContext.current

    // Same seed => same option order, so rotation never reshuffles the screen.
    val questions = remember(level.id, seed) {
        level.questions.mapIndexed { i, question -> shuffleOptions(question, Random(seed + i)) }
    }

    var index by rememberSaveable(level.id, seed) { mutableStateOf(0) }
    var score by rememberSaveable(level.id, seed) { mutableStateOf(0) }
    var answered by rememberSaveable(level.id, seed) { mutableStateOf(false) }
    var selected by rememberSaveable(level.id, seed) { mutableStateOf(-1) }
    var showQuitDialog by rememberSaveable { mutableStateOf(false) }

    BackHandler { showQuitDialog = true }

    val question = questions[index]
    val isLast = index == questions.size - 1
    val progress by animateFloatAsState(
        targetValue = (index + if (answered) 1 else 0) / questions.size.toFloat(),
        label = "quizProgress"
    )

    GradientScreen {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = level.title,
                    modifier = Modifier.weight(1f),
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Badge("${index + 1}/${questions.size}")
            }

            Spacer(Modifier.height(14.dp))
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(8.dp)),
                color = Color.White,
                trackColor = Color(0x66FFFFFF)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                WhiteCard(Modifier.fillMaxWidth()) {
                    Text(
                        text = question.text,
                        color = TextPrimary,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(18.dp))

                    question.options.forEachIndexed { i, option ->
                        OptionRow(
                            text = option,
                            isSelected = selected == i,
                            isCorrect = answered && i == question.correctIndex,
                            isWrong = answered && i == selected && i != question.correctIndex,
                            enabled = !answered,
                            onClick = { selected = i }
                        )
                        Spacer(Modifier.height(10.dp))
                    }

                    if (answered) {
                        val correct = selected == question.correctIndex
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = if (correct) "Correct!"
                            else "Wrong! Correct answer: ${question.options[question.correctIndex]}",
                            color = if (correct) CorrectGreen else WrongRed,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            PrimaryButton(
                text = when {
                    !answered -> "Submit"
                    !isLast -> "Next"
                    else -> "Finish"
                },
                onClick = {
                    when {
                        !answered -> {
                            if (selected == -1) {
                                Toast.makeText(context, "Please select an option", Toast.LENGTH_SHORT).show()
                            } else {
                                answered = true
                                if (selected == question.correctIndex) score++
                            }
                        }
                        !isLast -> {
                            index++
                            answered = false
                            selected = -1
                        }
                        else -> onFinished(score)
                    }
                }
            )
        }
    }

    if (showQuitDialog) {
        AlertDialog(
            onDismissRequest = { showQuitDialog = false },
            title = { Text("Quit quiz?") },
            text = { Text("Your progress in this quiz will be lost.") },
            confirmButton = {
                TextButton(onClick = onQuit) { Text("Quit", color = WrongRed) }
            },
            dismissButton = {
                TextButton(onClick = { showQuitDialog = false }) {
                    Text("Keep playing", color = BrandPrimary)
                }
            }
        )
    }
}

@Composable
private fun OptionRow(
    text: String,
    isSelected: Boolean,
    isCorrect: Boolean,
    isWrong: Boolean,
    enabled: Boolean,
    onClick: () -> Unit
) {
    val (backgroundColor, borderColor) = when {
        isCorrect -> CorrectGreenSoft to CorrectGreen
        isWrong -> WrongRedSoft to WrongRed
        isSelected -> BrandSoft to BrandPrimary
        else -> OptionBackground to BrandSoft
    }
    val shape = RoundedCornerShape(16.dp)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(backgroundColor)
            .border(2.dp, borderColor, shape)
            .selectable(
                selected = isSelected,
                enabled = enabled,
                role = Role.RadioButton,
                onClick = onClick
            )
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = null,
            colors = RadioButtonDefaults.colors(selectedColor = BrandPrimary)
        )
        Spacer(Modifier.width(12.dp))
        Text(
            text = text,
            color = TextPrimary,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

private fun shuffleOptions(question: Question, random: Random): Question {
    val order = question.options.indices.shuffled(random)
    return Question(
        text = question.text,
        options = order.map { question.options[it] },
        correctIndex = order.indexOf(question.correctIndex)
    )
}
