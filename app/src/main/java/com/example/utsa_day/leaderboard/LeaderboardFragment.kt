package com.example.utsa_day.leaderboard

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.utsa_day.R
import com.example.utsa_day.databinding.FragmentLeaderboardBinding
import com.example.utsa_day.leaderboard.adapters.QuizzesRecyclerAdapter
import com.example.utsa_day.leaderboard.model.data.Quiz
import com.example.utsa_day.leaderboard.model.data.Quizzes
import com.example.utsa_day.leaderboard.quiz.QuizFragment

class LeaderboardFragment : Fragment() {

    private val TAG = "LeaderboardFragment"

    private lateinit var binding: FragmentLeaderboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLeaderboardBinding.inflate(
            inflater, container, false
        )

        val quizzes = Quizzes.getQuizzes(resources)
        val quizAdapter = QuizzesRecyclerAdapter {
            it.onQuizClicked()
        }
        binding.quizzesRecyclerView.adapter = quizAdapter
        Log.i(TAG, quizzes.toString())
        quizAdapter.submitList(quizzes)

        return binding.root
    }

    private fun Quiz.onQuizClicked() {
        val bundle = bundleOf("quizSelected" to this)

        childFragmentManager.commit {
            setReorderingAllowed(true)
            add<QuizFragment>(R.id.quiz_frag_container, args = bundle)
        }

        binding.quizFragContainer.visibility = View.VISIBLE
    }
}