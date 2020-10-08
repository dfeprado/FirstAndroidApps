package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

const val LOG_TAG = "GameViewModel";
private val CORRECT_BUZZ_PATTERN = longArrayOf(100, 100, 100, 100, 100, 100)
private val PANIC_BUZZ_PATTERN = longArrayOf(0, 200)
private val GAME_OVER_BUZZ_PATTERN = longArrayOf(0, 2000)
private val NO_BUZZ_PATTERN = longArrayOf(0)

enum class BuzzType(val pattern: LongArray) {
    CORRECT(CORRECT_BUZZ_PATTERN),
    GAME_OVER(GAME_OVER_BUZZ_PATTERN),
    COUNTDOWN_PANIC(PANIC_BUZZ_PATTERN),
    NO_BUZZ(NO_BUZZ_PATTERN)
}

class GameViewModel: ViewModel() {
    // The current word
    private val _word = MutableLiveData<String>();
    val word: LiveData<String>
        get() = _word;

    // The current score
    private val _score = MutableLiveData<Int>();
    val score: LiveData<Int>
        get() = _score;

    private val _gameFinishEvent = MutableLiveData<Boolean>();
    val gameFinishEvent: LiveData<Boolean>
        get() = _gameFinishEvent;

    private val _buzzEvent = MutableLiveData<BuzzType>();
    val buzzEvent: LiveData<BuzzType>
        get() = _buzzEvent;

    private val _countdownTimer = MutableLiveData<Long>();
    val countdownTimer: LiveData<Long>
        get() = _countdownTimer;

    val countdownTimerString = Transformations.map(countdownTimer) { time: Long ->
        DateUtils.formatElapsedTime(time);
    }

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    companion object {
        const val DONE = 0L;
        const val ONE_SECOND = 1000L;
        const val COUNTDOWN_TIME = 10000L;
    }

    private val timer: CountDownTimer;

    init {
        Log.i(LOG_TAG, "Created!");
        resetList()
        nextWord()
        _score.value = 0;
        _gameFinishEvent.value = false;
        _countdownTimer.value = COUNTDOWN_TIME/1000;
        _buzzEvent.value = BuzzType.NO_BUZZ;

        timer = object: CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
            override fun onTick(msUntilFinish: Long) {
                val newValue: Long = msUntilFinish/1000;
                _countdownTimer.value = newValue;
                if (newValue <= 5L)
                    _buzzEvent.value = BuzzType.COUNTDOWN_PANIC;

            }

            override fun onFinish() {
                _gameFinishEvent.value = true;
            }

        }.start();
    }

    override fun onCleared() {
        super.onCleared();
        Log.i(LOG_TAG, "Cleared!")
        timer.cancel();
    }

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            resetList();
        } else {
            _word.value = wordList.removeAt(0)
        }
    }

    fun onSkip() {
        _score.value = score.value?.minus(1);
        nextWord();
    }

    fun onCorrect() {
        _score.value = score.value?.plus(1);
        _buzzEvent.value = BuzzType.CORRECT;
        nextWord();
    }

    fun gameFinishCompleted() {
        _gameFinishEvent.value = false;
    }

    fun clearBuzzEvent() {
        _buzzEvent.value = BuzzType.NO_BUZZ;
    }
}