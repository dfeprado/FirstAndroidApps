package com.example.android.guesstheword.screens.score

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class ScoreViewModel(private val finScore: Int, private val navController: NavController): ViewModel() {
    private val _finalScore = MutableLiveData<Int>();
    val finalScore: LiveData<Int>
        get() = _finalScore;

    init {
        _finalScore.value = finScore;
    }

    fun onPlayAgain() {
        navController.navigate(ScoreFragmentDirections.actionRestart());
    }
}