package com.example.utsa_day.leaderboard

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.utsa_day.MainViewModel
import com.example.utsa_day.R
import com.example.utsa_day.databinding.FragmentLeaderboardBinding
import com.example.utsa_day.leaderboard.adapters.LeaderboardRecyclerAdapter
import com.example.utsa_day.leaderboard.adapters.QuizzesRecyclerAdapter
import com.example.utsa_day.leaderboard.model.data.Quiz
import com.example.utsa_day.leaderboard.quiz.QuizFragment
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis

class LeaderboardFragment : Fragment() {

    private val TAG = "LeaderboardFragment"

    private lateinit var binding: FragmentLeaderboardBinding
    private val sharedViewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true).apply {
            duration = 2000
        }
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false).apply {
            duration = 1000
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLeaderboardBinding.inflate(
            inflater, container, false
        )

        val quizzes = Quiz.getQuizzes(resources)
        val quizAdapter = QuizzesRecyclerAdapter {
            it.onQuizClicked()
        }
        val leaderboardAdapter = LeaderboardRecyclerAdapter()
        binding.quizzesRecyclerView.adapter = quizAdapter
        binding.leaderboardRecyclerView.adapter = leaderboardAdapter
        quizAdapter.submitList(quizzes)

        sharedViewModel.removeQuiz.observe(viewLifecycleOwner) {
            if (it != null) {
                val quizFragment = childFragmentManager.findFragmentById(R.id.quiz_frag_container)
                if (quizFragment != null) {
                    binding.quizBackgroundView.visibility = View.GONE
                    ObjectAnimator.ofFloat(
                        binding.quizBackgroundView, "alpha", 0f
                    ).start()
                    childFragmentManager.beginTransaction().remove(quizFragment).commit()
                }
            }
        }

        sharedViewModel.updateLeaderboard.observe(viewLifecycleOwner) {
            leaderboardAdapter.submitList(sharedViewModel.leaderboard)
        }

        sharedViewModel.updateLeaderboard()

        return binding.root
    }

    private fun Quiz.onQuizClicked() {
        val bundle = bundleOf("quizSelected" to this)
        sharedViewModel.setRemoveQuiz(false)

        childFragmentManager.commit {
            setReorderingAllowed(true)
            add<QuizFragment>(R.id.quiz_frag_container, args = bundle)
        }

        binding.quizBackgroundView.visibility = View.VISIBLE
        ObjectAnimator.ofFloat(
            binding.quizBackgroundView, "alpha", 1f
        ).start()

        binding.quizFragContainer.visibility = View.VISIBLE
    }
}