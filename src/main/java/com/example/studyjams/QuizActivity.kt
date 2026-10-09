package com.example.studyjams

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.LinearProgressIndicator
import kotlin.random.Random

/**
 * Runs one quiz level. Flow per question: pick an option -> Submit (answer is revealed in
 * green/red) -> Next / Finish. Survives rotation and shuffles the answer options each attempt.
 */
class QuizActivity : AppCompatActivity() {

    private lateinit var level: Level
    private lateinit var questions: List<Question>

    private lateinit var levelNameText: TextView
    private lateinit var questionNumberText: TextView
    private lateinit var questionText: TextView
    private lateinit var feedbackText: TextView
    private lateinit var optionsGroup: RadioGroup
    private lateinit var optionButtons: List<RadioButton>
    private lateinit var nextButton: MaterialButton
    private lateinit var progressBar: LinearProgressIndicator

    private var seed = 0L
    private var currentIndex = 0
    private var score = 0
    private var answered = false
    private var selectedIndex = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val found = QuizData.byId(intent.getStringExtra(EXTRA_LEVEL_ID))
        if (found == null) {
            finish()
            return
        }
        level = found

        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz)
        applyInsets(findViewById(R.id.quizRoot))

        levelNameText = findViewById(R.id.levelNameText)
        questionNumberText = findViewById(R.id.questionNumberText)
        questionText = findViewById(R.id.questionText)
        feedbackText = findViewById(R.id.feedbackText)
        optionsGroup = findViewById(R.id.optionsGroup)
        nextButton = findViewById(R.id.nextButton)
        progressBar = findViewById(R.id.progressBar)
        optionButtons = listOf(
            findViewById<RadioButton>(R.id.option1),
            findViewById<RadioButton>(R.id.option2),
            findViewById<RadioButton>(R.id.option3),
            findViewById<RadioButton>(R.id.option4)
        )

        // Restore state after rotation, otherwise start a fresh attempt.
        if (savedInstanceState != null) {
            seed = savedInstanceState.getLong(KEY_SEED)
            currentIndex = savedInstanceState.getInt(KEY_INDEX)
            score = savedInstanceState.getInt(KEY_SCORE)
            answered = savedInstanceState.getBoolean(KEY_ANSWERED)
            selectedIndex = savedInstanceState.getInt(KEY_SELECTED, -1)
        } else {
            seed = System.nanoTime()
        }

        // Same seed => same option order, so rotation never reshuffles the screen.
        questions = level.questions.mapIndexed { i, question ->
            shuffleOptions(question, Random(seed + i))
        }

        levelNameText.text = level.title
        progressBar.max = questions.size
        nextButton.setOnClickListener { onButtonClicked() }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                AlertDialog.Builder(this@QuizActivity)
                    .setTitle("Quit quiz?")
                    .setMessage("Your progress in this quiz will be lost.")
                    .setPositiveButton("Quit") { _, _ -> finish() }
                    .setNegativeButton("Keep playing", null)
                    .show()
            }
        })

        showQuestion()
        if (answered) revealAnswer()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(KEY_SEED, seed)
        outState.putInt(KEY_INDEX, currentIndex)
        outState.putInt(KEY_SCORE, score)
        outState.putBoolean(KEY_ANSWERED, answered)
        outState.putInt(KEY_SELECTED, selectedIndex)
    }

    private fun shuffleOptions(question: Question, random: Random): Question {
        val order = question.options.indices.shuffled(random)
        return Question(
            text = question.text,
            options = order.map { question.options[it] },
            correctIndex = order.indexOf(question.correctIndex)
        )
    }

    private fun showQuestion() {
        val question = questions[currentIndex]

        questionText.text = question.text
        questionNumberText.text = "${currentIndex + 1}/${questions.size}"
        progressBar.setProgressCompat(currentIndex, true)

        feedbackText.visibility = android.view.View.GONE
        nextButton.text = "Submit"

        optionsGroup.clearCheck()
        for ((i, button) in optionButtons.withIndex()) {
            button.text = question.options[i]
            button.isClickable = true
            setOptionBackground(button, R.drawable.option_bg)
        }

        // Re-select the user's pick after a rotation.
        if (answered && selectedIndex in optionButtons.indices) {
            optionButtons[selectedIndex].isChecked = true
        }
    }

    private fun onButtonClicked() {
        if (!answered) {
            val checkedIndex = optionButtons.indexOfFirst { it.id == optionsGroup.checkedRadioButtonId }
            if (checkedIndex == -1) {
                Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show()
                return
            }
            selectedIndex = checkedIndex
            answered = true
            if (checkedIndex == questions[currentIndex].correctIndex) score++
            revealAnswer()
        } else if (currentIndex < questions.size - 1) {
            currentIndex++
            answered = false
            selectedIndex = -1
            showQuestion()
        } else {
            finishQuiz()
        }
    }

    /** Locks the options, colours right/wrong, and shows feedback. */
    private fun revealAnswer() {
        val question = questions[currentIndex]

        for ((i, button) in optionButtons.withIndex()) {
            button.isClickable = false
            when {
                i == question.correctIndex -> setOptionBackground(button, R.drawable.option_correct)
                i == selectedIndex -> setOptionBackground(button, R.drawable.option_wrong)
            }
        }

        val correct = selectedIndex == question.correctIndex
        feedbackText.visibility = android.view.View.VISIBLE
        feedbackText.text =
            if (correct) "Correct!" else "Wrong! Correct answer: ${question.options[question.correctIndex]}"
        feedbackText.setTextColor(
            ContextCompat.getColor(this, if (correct) R.color.correct_green else R.color.wrong_red)
        )

        progressBar.setProgressCompat(currentIndex + 1, true)
        nextButton.text = if (currentIndex < questions.size - 1) "Next" else "Finish"
    }

    /** setBackgroundResource can reset padding on some drawables, so restore it. */
    private fun setOptionBackground(button: RadioButton, resId: Int) {
        val l = button.paddingLeft
        val t = button.paddingTop
        val r = button.paddingRight
        val b = button.paddingBottom
        button.setBackgroundResource(resId)
        button.setPadding(l, t, r, b)
    }

    private fun finishQuiz() {
        val isNewBest = Prefs.saveBest(this, level.id, score)
        startActivity(
            Intent(this, ResultActivity::class.java)
                .putExtra(EXTRA_LEVEL_ID, level.id)
                .putExtra(ResultActivity.EXTRA_SCORE, score)
                .putExtra(ResultActivity.EXTRA_TOTAL, questions.size)
                .putExtra(ResultActivity.EXTRA_NEW_BEST, isNewBest)
        )
        finish()
    }

    companion object {
        const val EXTRA_LEVEL_ID = "LEVEL_ID"
        private const val KEY_SEED = "seed"
        private const val KEY_INDEX = "index"
        private const val KEY_SCORE = "score"
        private const val KEY_ANSWERED = "answered"
        private const val KEY_SELECTED = "selected"
    }
}
