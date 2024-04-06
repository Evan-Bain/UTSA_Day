package com.example.utsa_day

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _removeQuiz = MutableLiveData(true)
    val removeQuiz: LiveData<Boolean>
        get() = _removeQuiz

    fun setRemoveQuiz() {
        _removeQuiz.value = !(removeQuiz.value ?: false)
    }
}