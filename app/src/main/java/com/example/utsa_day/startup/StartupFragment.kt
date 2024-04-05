package com.example.utsa_day.startup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.utsa_day.R
import com.example.utsa_day.databinding.FragmentStartupBinding

class StartupFragment : Fragment() {

    private lateinit var binding: FragmentStartupBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartupBinding.inflate(
            inflater, container, false
        )

        binding.navigateToLeaderboardButton.setOnClickListener {
            findNavController().navigate(R.id.startup_action)
        }

        return binding.root
    }
}