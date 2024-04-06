package com.example.utsa_day.leaderboard.model.data

import android.content.res.Resources
import android.os.Parcelable
import com.example.utsa_day.R
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


@Parcelize
data class Quiz(
    private val resources: @RawValue Resources,
    private val position: Int
) : Parcelable {
    private val _correctAnswers = mutableListOf<String>()
    val correctAnswers: List<String>
        get() = _correctAnswers

    fun getQuizStrings(): HashMap<Int, List<String>> {
        val questions = getQuizQuestions()
        val answers = getQuizAnswers()

        val hashMap = HashMap<Int, List<String>>()

        for(i in 0..3) {
            val correctAnswer = answers[0]
            _correctAnswers.add(correctAnswer)

            val shuffledAnswers = listOf(
                correctAnswer,
                answers[i * 4 + 1],
                answers[i * 4 + 2],
                answers[i * 4 + 3],
            ).shuffled()

            hashMap[i] = listOf(
                questions[i],
                shuffledAnswers[0],
                shuffledAnswers[1],
                shuffledAnswers[2],
                shuffledAnswers[3],
            )
        }
        return hashMap
    }

    private fun getQuizQuestions(): Array<String> {
        return when(position) {
            0 -> resources.getStringArray(R.array.general_UTSA_Day_hardware_questions_array)
            1 -> resources.getStringArray(R.array.general_UTSA_Day_development_questions_array)
            2 -> resources.getStringArray(R.array.general_UTSA_Day_languages_questions_array)
            3 -> resources.getStringArray(R.array.general_UTSA_Day_UTSA_questions_array)
            4 -> resources.getStringArray(R.array.general_UTSA_Day_misc_questions_array)
            else -> arrayOf()
        }
    }

    private fun getQuizAnswers(): Array<String> {
        return when(position) {
            0 -> resources.getStringArray(R.array.general_UTSA_Day_hardware_answers_array)
            1 -> resources.getStringArray(R.array.general_UTSA_Day_development_answers_array)
            2 -> resources.getStringArray(R.array.general_UTSA_Day_languages_answers_array)
            3 -> resources.getStringArray(R.array.general_UTSA_Day_UTSA_answers_array)
            4 -> resources.getStringArray(R.array.general_UTSA_Day_misc_answers_array)
            else -> arrayOf()
        }
    }
}