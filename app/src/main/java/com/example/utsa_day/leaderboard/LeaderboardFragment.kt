package com.example.utsa_day.leaderboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.utsa_day.databinding.FragmentLeaderboardBinding
import com.example.utsa_day.leaderboard.adapters.QuizzesRecyclerAdapter

class LeaderboardFragment : Fragment() {

    private lateinit var binding: FragmentLeaderboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLeaderboardBinding.inflate(
            inflater, container, false
        )



        return binding.root
    }
}