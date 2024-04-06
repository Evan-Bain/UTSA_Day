package com.example.utsa_day.leaderboard.model.data

import android.content.res.Resources
import com.example.utsa_day.R

data class Quizzes(
    val name: String,
    val quiz: Quiz
) {
    companion object {
        fun getQuizzes(resources: Resources): List<Quizzes> {
            return listOf(
                Quizzes(
                    resources.getString(R.string.hardware_quiz_title),
                    Quiz(resources, 0)
                ),
                Quizzes(
                    resources.getString(R.string.development_quiz_title),
                    Quiz(resources, 1)
                ),
                Quizzes(
                    resources.getString(R.string.languages_quiz_title),
                    Quiz(resources, 2)
                ),
                Quizzes(
                    resources.getString(R.string.misc_quiz_title),
                    Quiz(resources, 3)
                ),
                Quizzes(
                    resources.getString(R.string.utsa_day_quiz_title),
                    Quiz(resources, 5)
                )
            )
        }
    }
}
