package com.example.studyjams

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

/** Lets the player pick one of the quiz levels. */
class LevelActivity : AppCompatActivity() {

    private lateinit var container: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_levels)
        applyInsets(findViewById(R.id.levelsRoot))

        container = findViewById(R.id.levelContainer)

        val name = Prefs.username(this).ifBlank { "Guest" }
        findViewById<TextView>(R.id.greetingText).text = "Hello, $name!"

        findViewById<MaterialButton>(R.id.signOutButton).setOnClickListener {
            Prefs.setUsername(this, "")
            startActivity(
                Intent(this, LoginActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            )
            finish()
        }
    }

    // Rebuilt every time we come back so the "best score" labels stay up to date.
    override fun onResume() {
        super.onResume()
        container.removeAllViews()
        val inflater = LayoutInflater.from(this)

        for (level in QuizData.levels) {
            val card = inflater.inflate(R.layout.item_level, container, false)
            card.findViewById<TextView>(R.id.levelTitle).text = level.title
            card.findViewById<TextView>(R.id.levelDesc).text = level.description
            card.findViewById<TextView>(R.id.levelBadge).text = "${level.questions.size} questions"

            val best = Prefs.best(this, level.id)
            card.findViewById<TextView>(R.id.levelBest).text =
                if (best >= 0) "Best score: $best/${level.questions.size}" else "Not attempted yet"

            card.findViewById<MaterialButton>(R.id.levelStart).setOnClickListener {
                startActivity(
                    Intent(this, QuizActivity::class.java)
                        .putExtra(QuizActivity.EXTRA_LEVEL_ID, level.id)
                )
            }
            container.addView(card)
        }
    }
}
