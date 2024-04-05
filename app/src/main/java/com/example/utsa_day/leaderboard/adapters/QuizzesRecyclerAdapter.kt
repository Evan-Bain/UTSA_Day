package com.example.utsa_day.leaderboard.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.utsa_day.databinding.QuizzesRecyclerLayoutBinding
import com.example.utsa_day.leaderboard.model.data.Quiz
import com.example.utsa_day.leaderboard.model.data.Quizzes

class QuizzesRecyclerAdapter(private val onButtonClicked: (Quiz) -> Unit) :
    ListAdapter<Quizzes, QuizzesRecyclerAdapter.ViewHolder>(QuizzesDiffCallback()) {

    inner class ViewHolder(val binding: QuizzesRecyclerLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(data: Quizzes) {
                binding.quizNameText.text = data.name
                binding.quizPointsText.text = data.points

                binding.takeQuizButton.setOnClickListener {
                    onButtonClicked(data.quiz)
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = QuizzesRecyclerLayoutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class QuizzesDiffCallback : DiffUtil.ItemCallback<Quizzes>() {
    override fun areItemsTheSame(oldItem: Quizzes, newItem: Quizzes): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Quizzes, newItem: Quizzes): Boolean {
        return oldItem == newItem
    }

}