package com.example.utsa_day.startup

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.utsa_day.MainViewModel
import com.example.utsa_day.R
import com.example.utsa_day.databinding.FragmentStartupBinding
import com.google.android.material.transition.MaterialSharedAxis
import java.util.Locale


class StartupFragment : Fragment() {

    private lateinit var binding: FragmentStartupBinding
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true).apply {
            duration = 2000
        }
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false).apply {
            duration = 1000
        }

        super.onCreate(savedInstanceState)
    }

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

        binding.languagesButton.setOnCheckedChangeListener { buttonView, isChecked ->
            mainViewModel.setLanguage(isChecked)
        }

        return binding.root
    }
}