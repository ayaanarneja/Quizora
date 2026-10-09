package com.example.studyjams

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

private enum class Screen { Splash, Login, Levels, Quiz, Result }

/**
 * Root composable: Splash -> Login -> Levels -> Quiz -> Result.
 * Navigation is plain state, saved across rotation with rememberSaveable.
 */
@Composable
fun QuizoraApp() {
    val context = LocalContext.current

    var screenName by rememberSaveable { mutableStateOf(Screen.Splash.name) }
    var username by rememberSaveable { mutableStateOf(Prefs.username(context)) }
    var levelId by rememberSaveable { mutableStateOf("") }
    var seed by rememberSaveable { mutableStateOf(0L) }
    var lastScore by rememberSaveable { mutableStateOf(0) }
    var newBest by rememberSaveable { mutableStateOf(false) }

    fun go(screen: Screen) {
        screenName = screen.name
    }

    fun startQuiz(id: String) {
        levelId = id
        seed = System.nanoTime() // new seed => fresh option shuffle for every attempt
        go(Screen.Quiz)
    }

    val level = QuizData.byId(levelId)
    val screen = Screen.valueOf(screenName)

    when {
        screen == Screen.Splash -> SplashScreen(onFinished = { go(Screen.Login) })

        screen == Screen.Login -> LoginScreen(
            onSignedIn = { name ->
                username = name
                go(Screen.Levels)
            }
        )

        screen == Screen.Quiz && level != null -> QuizScreen(
            level = level,
            seed = seed,
            onFinished = { score ->
                lastScore = score
                newBest = Prefs.saveBest(context, level.id, score)
                go(Screen.Result)
            },
            onQuit = { go(Screen.Levels) }
        )

        screen == Screen.Result && level != null -> ResultScreen(
            level = level,
            score = lastScore,
            total = level.questions.size,
            newBest = newBest,
            onTryAgain = { startQuiz(level.id) },
            onChooseLevel = { go(Screen.Levels) }
        )

        else -> LevelScreen(
            username = username,
            onStart = { startQuiz(it.id) },
            onSignOut = {
                Prefs.setUsername(context, "")
                username = ""
                go(Screen.Login)
            }
        )
    }
}
