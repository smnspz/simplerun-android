package it.rosani.simplerun.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RunViewModel @Inject constructor() : ViewModel() {
    private val _isRunning = MutableStateFlow(true)
    val isRunning: StateFlow<Boolean> = _isRunning

    private val _elapsedTime = MutableStateFlow(0L)
    val elapsedTime: StateFlow<Long> = _elapsedTime

    init {
        startTimer()
    }

    fun startTimer() {
        viewModelScope.launch {
            val startTime = System.currentTimeMillis()
            while (true) {
                if (_isRunning.value) {
                    _elapsedTime.value = System.currentTimeMillis() - startTime
                }
                delay(1000)
            }
        }
    }

    fun toggleRunning() {
        _isRunning.value = !_isRunning.value
    }
}