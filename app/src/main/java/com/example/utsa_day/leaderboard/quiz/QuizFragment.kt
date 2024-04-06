package com.example.utsa_day.leaderboard.quiz

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.utsa_day.R
import com.example.utsa_day.databinding.FragmentQuizBinding
import com.example.utsa_day.MainViewModel
import com.example.utsa_day.leaderboard.model.LeaderboardDatabase
import com.example.utsa_day.leaderboard.model.data.LeaderboardTable
import com.example.utsa_day.leaderboard.model.data.Quiz

class QuizFragment : Fragment() {

    private lateinit var binding: FragmentQuizBinding
    private val quizViewModel: QuizViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private val TAG = "QuizFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizBinding.inflate(
            inflater, container, false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = quizViewModel

        val quizStrings: Quiz = requireArguments().getParcelable("quizSelected")!!
        val quizText = quizStrings.getQuizStrings()
        val quizCorrectAnswers = quizStrings.correctAnswers

        binding.quizTitle.text = quizStrings.name
        binding.quizQuestion.movementMethod = ScrollingMovementMethod()

        binding.enterNameEditText.editText?.doOnTextChanged { text, _, _, _ ->

            if (text?.length == 1) {
                with(binding.quizMotionLayout) {
                    setTransition(R.id.quiz_start)
                    transitionToEnd()
                }
            }

            binding.quizFinishButton.isEnabled = text?.length != 0
        }

        quizViewModel.setQuizStringAnswers(
            listOf(
                quizText[0]?.get(0) ?: "ERROR",
                quizText[0]?.get(1) ?: "ERROR",
                quizText[0]?.get(2) ?: "ERROR",
                quizText[0]?.get(3) ?: "ERROR",
                quizText[0]?.get(4) ?: "ERROR"
            )
        )

        quizViewModel.quizVisible.observe(viewLifecycleOwner) {
            if (!it) {
                with(mainViewModel) {
                    insertPerson(LeaderboardTable(
                        quizViewModel.name.value!!,
                        quizViewModel.answersCorrect * 5)
                    )
                    setRemoveQuiz(true)
                }
            }
        }

        quizViewModel.nextButtonEnabled.observe(viewLifecycleOwner) {

            with(binding.quizMotionLayout) {
                setTransition(R.id.quiz_next_question)
                if (it) {
                    transitionToEnd()
                } else {
                    transitionToStart()
                }
            }
        }

        quizViewModel.nextButtonClicked.observe(viewLifecycleOwner) {

            if (!quizViewModel.initialized) {
                quizViewModel.setInitialized()
                quizViewModel.setName(binding.enterNameEditText.editText?.text.toString())
                with(binding.quizMotionLayout) {
                    setTransition(R.id.quiz_end)
                    transitionToEnd()
                }
                quizViewModel.resetNextButton()
                return@observe
            }

            //make sure quiz is visible or main thread is stalled continuously
            when (it) {
                0 -> return@observe
                null -> {
                    quizViewModel.resetAnswersCorrect()
                    binding.quizRadioGroup.clearCheck()
                    binding.quizMotionLayout.setTransition(R.id.retry)
                    return@observe
                }
            }

            val correctAnswer = quizCorrectAnswers[it - 1]
            when (quizViewModel.radioButtonClicked.value) {
                0 -> {
                    if (binding.quizRadioButton0.text == correctAnswer)
                        quizViewModel.updateAnswerCorrect()
                }
                1 -> {
                    if (binding.quizRadioButton1.text == correctAnswer)
                        quizViewModel.updateAnswerCorrect()
                }
                2 -> {
                    if (binding.quizRadioButton2.text == correctAnswer)
                        quizViewModel.updateAnswerCorrect()
                }
                3 -> {
                    if (binding.quizRadioButton3.text == correctAnswer)
                        quizViewModel.updateAnswerCorrect()
                }
            }

            quizViewModel.setRadioButtonClicked(binding.quizRadioGroup.checkedRadioButtonId)
            binding.quizRadioGroup.clearCheck()

            //only set the text for quiz for how many questions there are
            if (it < 4) {

                //set text in quiz with dataBinding
                quizViewModel.setQuizStringAnswers(
                    listOf(
                        quizText[it]?.get(0) ?: "ERROR",
                        quizText[it]?.get(1) ?: "ERROR",
                        quizText[it]?.get(2) ?: "ERROR",
                        quizText[it]?.get(3) ?: "ERROR",
                        quizText[it]?.get(4) ?: "ERROR"
                    )
                )
            } else {
                if (quizViewModel.quizPassed) {
                    with(binding.quizMotionLayout) {
                        setTransition(R.id.quiz_passed)
                        transitionToEnd()
                    }
                } else {
                    with(binding.quizMotionLayout) {
                        setTransition(R.id.result_transition)
                        transitionToEnd {
                            setTransition(R.id.result_resize_transition)
                            transitionToEnd()
                        }
                    }

                    /*
                    NOTE: add points to the user
                    if (viewModel.answersCorrect > 3) {
                        sharedViewModel.updateActivated(
                            dinoPosition,
                            true
                        )
                        viewModel.setQuizPassed()
                    }
                     */
                }
            }
        }

        return binding.root
    }
}