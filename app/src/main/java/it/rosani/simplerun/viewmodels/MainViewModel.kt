package it.rosani.simplerun.viewmodels

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.rosani.simplerun.services.location_service.LocationServiceManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val locationServiceManager: LocationServiceManager
) : ViewModel() {

    private val _location = MutableStateFlow<Location?>(null)
    val location: StateFlow<Location?> = _location.asStateFlow()

    init {
        viewModelScope.launch {
            locationServiceManager.locationFlow.collect {
                _location.value = it
            }
        }
    }

    fun bindLocationService() = locationServiceManager.bindService()

    fun unbindLocationService() = locationServiceManager.unbindService()

    override fun onCleared() {
        super.onCleared()
        unbindLocationService()
    }
}