package com.example.studyjams

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)
        applyInsets(findViewById(R.id.resultRoot))

        val levelId = intent.getStringExtra(QuizActivity.EXTRA_LEVEL_ID)
        val level = QuizData.byId(levelId)
        val score = intent.getIntExtra(EXTRA_SCORE, 0)
        val total = intent.getIntExtra(EXTRA_TOTAL, 0)
        val newBest = intent.getBooleanExtra(EXTRA_NEW_BEST, false)
        val percent = if (total > 0) score * 100 / total else 0

        findViewById<TextView>(R.id.levelText).text = level?.title ?: "Quiz"
        findViewById<TextView>(R.id.scoreText).text = "$score/$total"
        findViewById<TextView>(R.id.percentText).text = "$percent%"
        findViewById<TextView>(R.id.messageText).text = when {
            total > 0 && score == total -> "Perfect score!"
            percent >= 60 -> "Great job!"
            percent >= 40 -> "Good effort - keep practising!"
            else -> "Good try - give it another go!"
        }

        val best = if (levelId != null) Prefs.best(this, levelId) else -1
        findViewById<TextView>(R.id.bestText).text = when {
            newBest -> "New best score!"
            best >= 0 -> "Best score: $best/$total"
            else -> ""
        }

        findViewById<MaterialButton>(R.id.playAgainButton).setOnClickListener {
            if (levelId != null) {
                startActivity(
                    Intent(this, QuizActivity::class.java)
                        .putExtra(QuizActivity.EXTRA_LEVEL_ID, levelId)
                )
            }
            finish()
        }
        findViewById<MaterialButton>(R.id.levelsButton).setOnClickListener { openLevels() }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() = openLevels()
        })
    }

    private fun openLevels() {
        startActivity(
            Intent(this, LevelActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        )
        finish()
    }

    companion object {
        const val EXTRA_SCORE = "SCORE"
        const val EXTRA_TOTAL = "TOTAL"
        const val EXTRA_NEW_BEST = "NEW_BEST"
    }
}
