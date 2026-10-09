package com.example.studyjams

import android.content.Context

/** Tiny SharedPreferences wrapper: remembers the username and the best score per level. */
object Prefs {
    private const val FILE = "studyjams_prefs"
    private const val KEY_USERNAME = "username"

    private fun sp(context: Context) =
        context.applicationContext.getSharedPreferences(FILE, Context.MODE_PRIVATE)

    fun username(context: Context): String = sp(context).getString(KEY_USERNAME, "") ?: ""

    fun setUsername(context: Context, name: String) {
        sp(context).edit().putString(KEY_USERNAME, name).apply()
    }

    /** Best score for [levelId], or -1 if the level was never completed. */
    fun best(context: Context, levelId: String): Int = sp(context).getInt("best_$levelId", -1)

    /** Stores [score] if it beats the old best. Returns true when it is a new (non-zero) best. */
    fun saveBest(context: Context, levelId: String, score: Int): Boolean {
        val old = best(context, levelId)
        if (score > old) {
            sp(context).edit().putInt("best_$levelId", score).apply()
        }
        return score > maxOf(old, 0)
    }
}
