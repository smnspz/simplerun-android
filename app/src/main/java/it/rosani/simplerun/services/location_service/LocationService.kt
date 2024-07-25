package it.rosani.simplerun.services.location_service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.Binder
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import it.rosani.simplerun.ext.isLocationPermissionGranted

class LocationService : Service() {
    val location = MutableLiveData<Location?>(null)
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.lastLocation?.let { onLocationChanged(it) }
        }
    }
    private var lastLocation: Location? = null
        set(value) {
            field = value
            location.postValue(value)
        }

    override fun onBind(intent: Intent?) = LocationBinder()

    override fun onCreate() {
        super.onCreate()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        requestLocationUpdates()
        return START_STICKY
    }

    @SuppressLint("MissingPermission")
    private fun requestLocationUpdates() {
        if (applicationContext.isLocationPermissionGranted()) {
            val locationRequest =
                LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 3000).apply {
                    setMinUpdateDistanceMeters(10f)
                    setMinUpdateIntervalMillis(1000)
                    setMaxUpdateDelayMillis(3000)
                    setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
                }.build()

            try {
                fusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.getMainLooper()
                )
                Log.d("LocationService", "Requested location updates")
            } catch(e: Exception) {
                Log.e("LocationService", "Failed to request location updates", e)
            }
        }
    }

    private fun onLocationChanged(location: Location) {
        if (lastLocation == null) {
            lastLocation = location
            return
        }

        lastLocation = location
    }

    inner class LocationBinder : Binder() {
        val service: LocationService
            get() = this@LocationService
    }
}