package com.example.studyjams

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class QuizDataTest {
    @Test
    fun everyQuestionIsWellFormed() {
        assertTrue(QuizData.levels.isNotEmpty())
        for (level in QuizData.levels) {
            assertEquals("${level.id} should have 5 questions", 5, level.questions.size)
            for (q in level.questions) {
                assertEquals("'${q.text}' needs 4 options", 4, q.options.size)
                assertTrue("'${q.text}' correctIndex out of range", q.correctIndex in q.options.indices)
                assertEquals("'${q.text}' has duplicate options", q.options.size, q.options.toSet().size)
            }
        }
    }

    @Test
    fun levelIdsAreUnique() {
        assertEquals(QuizData.levels.size, QuizData.levels.map { it.id }.toSet().size)
    }
}
