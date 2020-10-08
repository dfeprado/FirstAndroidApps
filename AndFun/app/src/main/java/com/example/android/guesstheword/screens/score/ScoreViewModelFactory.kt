package com.example.android.guesstheword.screens.score

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController

class ScoreViewModelFactory(private val finalScore: Int, private val navController: NavController): ViewModelProvider.Factory {
    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScoreViewModel::class.java)) {
            return ScoreViewModel(finalScore, navController) as T;
        }
        throw IllegalArgumentException("Unknown ViewModel class");
    }
}