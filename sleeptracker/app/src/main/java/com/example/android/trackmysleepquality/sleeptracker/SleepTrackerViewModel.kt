/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.trackmysleepquality.sleeptracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.formatNights
import kotlinx.coroutines.*

/**
 * ViewModel for SleepTrackerFragment.
 */
class SleepTrackerViewModel(
        val database: SleepDatabaseDao,
        application: Application
) : AndroidViewModel(application) {
    private var viewmodelCoroutines: Job = Job();

    override fun onCleared() {
        super.onCleared();
        viewmodelCoroutines.cancel();
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewmodelCoroutines);

    private val tonight = MutableLiveData<SleepNight?>();

    private val nights = database.getAllNights();

    val nightsString = Transformations.map(nights) {
        formatNights(it, application.resources);
    }

    private val _navigateToSleepQuality = MutableLiveData<SleepNight?>();
    val navigateToSleepQuality: LiveData<SleepNight?>
        get() = _navigateToSleepQuality;

    init {
        initializeTonight();
    }

    fun initializeTonight() {
        uiScope.launch {
            tonight.value = getTonightFromDatabase();
        }
    }

    private suspend fun getTonightFromDatabase(): SleepNight? {
        return withContext(Dispatchers.IO) {
            var night = database.getTonight();
            if (night?.startTimeMilli != night?.endTimeMilli)
                night = null;
            night;
        }
    }

    fun startTracking() {
        uiScope.launch {
            val newNight = SleepNight();

            insert(newNight);

            tonight.value = getTonightFromDatabase();
        }
    }

    private suspend fun insert(night: SleepNight) {
        withContext(Dispatchers.IO) {
            database.insert(night);
        }
    }

    fun stopTracking() {
        uiScope.launch {
            val oldNight = tonight.value ?: return@launch;
            oldNight.endTimeMilli = System.currentTimeMillis();
            update(oldNight);
            _navigateToSleepQuality.value = oldNight;
        }
    }

    fun doneNavigating() {
        _navigateToSleepQuality.value = null;
    }

    private suspend fun update(night: SleepNight) {
        withContext(Dispatchers.IO) {
            database.update(night);
        }
    }

    fun clear() {
        uiScope.launch {
            doClear();
            tonight.value = null;
        }
    }

    private suspend fun doClear() {
        withContext(Dispatchers.IO) {
            database.clear();
        }
    }
}

