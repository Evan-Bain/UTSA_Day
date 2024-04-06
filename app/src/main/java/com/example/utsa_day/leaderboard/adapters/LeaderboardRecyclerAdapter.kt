package com.example.utsa_day.leaderboard.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.utsa_day.databinding.LeaderboardRecyclerLayoutBinding
import com.example.utsa_day.databinding.QuizzesRecyclerLayoutBinding
import com.example.utsa_day.leaderboard.model.data.LeaderboardTable
import com.example.utsa_day.leaderboard.model.data.Quiz

class LeaderboardRecyclerAdapter :
    ListAdapter<LeaderboardTable, LeaderboardRecyclerAdapter.ViewHolder>(LeaderboardDiffCallback()) {

    inner class ViewHolder(val binding: LeaderboardRecyclerLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

            val placementText = binding.placementText

            fun bind(data: LeaderboardTable) {
                binding.nameText.text = data.name
                binding.pointsText.text = String.format("%d pts", data.score)
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LeaderboardRecyclerLayoutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.placementText.text = String.format("%d. ", position + 1)
    }
}

class LeaderboardDiffCallback : DiffUtil.ItemCallback<LeaderboardTable>() {
    override fun areItemsTheSame(oldItem: LeaderboardTable, newItem: LeaderboardTable): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: LeaderboardTable, newItem: LeaderboardTable): Boolean {
        return oldItem == newItem
    }

}