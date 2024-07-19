package it.rosani.simplerun.services.location_service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.location.Location
import android.os.IBinder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LocationServiceManager(private val context: Context) {
    private var isLocationServiceBound = false
    private var locationService: LocationService? = null

    private val _locationFlow = MutableStateFlow<Location?>(null)
    val locationFlow: StateFlow<Location?> = _locationFlow

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as LocationService.LocationBinder
            locationService = binder.service
            isLocationServiceBound = true

            locationService?.location?.observeForever { location ->
                _locationFlow.value = location
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isLocationServiceBound = false
            locationService = null
        }
    }

    fun bindService() {
        val intent = Intent(context, LocationService::class.java)
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    fun unbindService() {
        if (isLocationServiceBound) {
            context.unbindService(connection)
            isLocationServiceBound = false
        }
    }
}