package com.example.utsa_day

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.utsa_day.leaderboard.model.LeaderboardDao
import com.example.utsa_day.leaderboard.model.data.LeaderboardTable
import com.example.utsa_day.leaderboard.quiz.QuizViewModel
import kotlinx.coroutines.launch

class MainViewModel(
    private val dataSource: LeaderboardDao
) : ViewModel() {

    private val _removeQuiz = MutableLiveData(false)
    val removeQuiz: LiveData<Boolean>
        get() = _removeQuiz

    fun setRemoveQuiz(value: Boolean) {
        _removeQuiz.value = value
    }

    private val _changeLanguage = MutableLiveData<Boolean>()
    val changeLanguage: LiveData<Boolean>
        get() = _changeLanguage

    fun setLanguage(value: Boolean) {
        _changeLanguage.value = value
    }

    fun insertPerson(person: LeaderboardTable) {
        viewModelScope.launch {
            dataSource.insert(person)
            updateLeaderboard()
        }
    }

    private var _leaderboard: List<LeaderboardTable> = emptyList()
    val leaderboard: List<LeaderboardTable>
        get() = _leaderboard

    private val _updateLeaderboard = MutableLiveData<Boolean>()
    val updateLeaderboard: LiveData<Boolean>
        get() = _updateLeaderboard

    fun updateLeaderboard() {
        viewModelScope.launch {
            _leaderboard = dataSource.getLeaderboard()
            _updateLeaderboard.value = !(updateLeaderboard.value ?: false)
        }
    }
}

class MainViewModelFactory(
    private val dataSource: LeaderboardDao,
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}