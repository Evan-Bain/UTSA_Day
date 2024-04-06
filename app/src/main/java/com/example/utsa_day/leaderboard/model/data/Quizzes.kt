package com.example.utsa_day.leaderboard.model.data

import android.content.res.Resources
import com.example.utsa_day.R

data class Quizzes(
    val name: String,
    val points: String,
    val quiz: Quiz
) {
    companion object {
        fun getQuizzes(resources: Resources): List<Quizzes> {
            return listOf(
                Quizzes(
                    resources.getString(R.string.hardware_quiz),
                    resources.getString(R.string.hardware_quiz_points),
                    Quiz(resources, 0)
                ),
                Quizzes(
                    resources.getString(R.string.development_quiz),
                    resources.getString(R.string.development_quiz_points),
                    Quiz(resources, 1)
                ),
                Quizzes(
                    resources.getString(R.string.languages_quiz),
                    resources.getString(R.string.languages_quiz_points),
                    Quiz(resources, 2)
                ),
                Quizzes(
                    resources.getString(R.string.history_quiz),
                    resources.getString(R.string.history_quiz_points),
                    Quiz(resources, 3)
                ),
                Quizzes(
                    resources.getString(R.string.misc_quiz),
                    resources.getString(R.string.misc_quiz_points),
                    Quiz(resources, 4)
                )
            )
        }
    }
}
