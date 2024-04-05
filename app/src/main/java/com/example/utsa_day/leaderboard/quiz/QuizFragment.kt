package com.example.utsa_day.leaderboard.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.utsa_day.R
import com.example.utsa_day.databinding.FragmentQuizBinding
import com.example.utsa_day.leaderboard.model.data.Quiz

class QuizFragment : Fragment() {

    private lateinit var binding: FragmentQuizBinding
    private val viewModel: QuizViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizBinding.inflate(
            inflater, container, false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val quizText: List<String> =
            requireArguments().getStringArray("quizText")!!.toList()
        val quizCorrectAnswers: List<String> =
            requireArguments().getStringArray("quizCorrectAnswers")!!.toList()

        viewModel.nextButtonEnabled.observe(viewLifecycleOwner) {
            with(binding.quizMotionLayout) {
                setTransition(R.id.quiz_next_question)
                if (it) {
                    transitionToEnd()
                } else {
                    transitionToStart()
                }
            }
        }

        viewModel.nextButtonClicked.observe(viewLifecycleOwner) {

            //make sure quiz is visible or main thread is stalled continuously
            when (it) {
                0 -> return@observe
                null -> {
                    viewModel.resetAnswersCorrect()
                    binding.quizRadioGroup.clearCheck()
                    binding.quizMotionLayout.setTransition(R.id.retry)
                    return@observe
                }
            }

            val correctAnswer = quizCorrectAnswers[it - 1]
            when (viewModel.radioButtonClicked.value) {
                0 -> {
                    if (binding.quizRadioButton0.text == correctAnswer)
                        viewModel.updateAnswerCorrect()
                }
                1 -> {
                    if (binding.quizRadioButton1.text == correctAnswer)
                        viewModel.updateAnswerCorrect()
                }
                2 -> {
                    if (binding.quizRadioButton2.text == correctAnswer)
                        viewModel.updateAnswerCorrect()
                }
                3 -> {
                    if (binding.quizRadioButton3.text == correctAnswer)
                        viewModel.updateAnswerCorrect()
                }
            }

            viewModel.setRadioButtonClicked(binding.quizRadioGroup.checkedRadioButtonId)
            binding.quizRadioGroup.clearCheck()

            //only set the text for quiz for how many questions there are
            if (it < 5) {

                //set text in quiz with dataBinding
                viewModel.setQuizStringAnswers(
                    listOf(
                        quizText[it],
                        quizText[it],
                        quizText[it],
                        quizText[it],
                        quizText[it],
                        quizText[it],
                    )
                )
            } else {
                if (viewModel.quizPassed) {
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