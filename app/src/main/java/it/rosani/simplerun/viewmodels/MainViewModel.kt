package it.rosani.simplerun.viewmodels

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import it.rosani.simplerun.services.location_service.LocationServiceManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val locationServiceManager = LocationServiceManager(application)
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