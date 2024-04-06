package com.example.utsa_day.leaderboard.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.utsa_day.R

class QuizViewModel : ViewModel() {
    private var _initialized = false
    val initialized: Boolean
        get() = _initialized

    fun setInitialized() {
        _initialized = true
    }

    private var _quizPassed = false
    val quizPassed: Boolean
        get() = _quizPassed

    fun setQuizPassed() {
        _quizPassed = true
    }

    //used in data binding to determine what radio button was clicked
    private val _radioButtonClicked = MutableLiveData<Int>()
    val radioButtonClicked: LiveData<Int>
        get() = _radioButtonClicked

    fun setRadioButtonClicked(position: Int) {

        _radioButtonClicked.value = when(position) {
            R.id.quiz_radio_button_0 -> 0
            R.id.quiz_radio_button_1 -> 1
            R.id.quiz_radio_button_2 -> 2
            R.id.quiz_radio_button_3 -> 3
            else -> -1
        }
    }

    val nextButtonEnabled: LiveData<Boolean> = radioButtonClicked.map {
        return@map it != -1
    }

    //determine what quiz question to display
    private val _nextButtonClicked = MutableLiveData<Int>()
    val nextButtonClicked: LiveData<Int>
        get() = _nextButtonClicked

    fun resetNextButton() {
        _nextButtonClicked.value = 0
    }

    /** indicate what page of quiz is displayed  **/
    fun nextButtonClicked(value: Int) {
        /*
        parameters:
        -1: passed when R.id.quiz_finish_button is clicked (data binding)
        0: passed at the of animation for exiting the quiz
        5: passed when user opens quiz of a previously passed quiz
        */

        _nextButtonClicked.value = when(value) {
            -1 -> (nextButtonClicked.value ?: 0) + 1
            0 -> null
            else -> {
                _answersCorrect = 5
                value
            }
        }
    }

    //question and answers for quiz
    private val quizStrings = MutableLiveData<List<String>>()

    //all answers and question updated with dataBinding
    val quizQuestion = quizStrings.map {
        return@map it[0]
    }

    val firstAnswer = quizStrings.map {
        return@map it[1]
    }

    val secondAnswer = quizStrings.map {
        return@map it[2]
    }

    val thirdAnswer = quizStrings.map {
        return@map it[3]
    }

    val fourthAnswer = quizStrings.map {
        return@map it[4]
    }

    /** update question and answers through dataBinding **/
    fun setQuizStringAnswers(list: List<String>) {
        quizStrings.value = list
    }

    //holds the value of how many questions are answered correctly
    private var _answersCorrect = 0
    val answersCorrect: Int
        get() = _answersCorrect

    fun updateAnswerCorrect() {
        _answersCorrect++
    }

    fun resetAnswersCorrect() {
        _answersCorrect = 0
    }

    val resultMessageText = nextButtonClicked.map {
        setResultText("Congratulations", "Unfortunately")
    }

    val resultText = nextButtonClicked.map {
        setResultText("You Passed", "You Failed")
    }

    val percentText = nextButtonClicked.map {
        "${_answersCorrect*20}%"
    }

    private fun setResultText(passText: String, failText: String): String {
        return if(_answersCorrect > 3) {
            passText
        } else {
            failText
        }
    }

    private val _quizVisible = MutableLiveData(true)
    val quizVisible: LiveData<Boolean>
        get() = _quizVisible

    fun setQuizVisible(value: Boolean) {
        _quizVisible.value = value
    }
}