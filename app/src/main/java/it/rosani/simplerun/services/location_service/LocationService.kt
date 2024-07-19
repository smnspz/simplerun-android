package it.rosani.simplerun.services.location_service

import android.Manifest
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
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

class LocationService : Service() {
    val location = MutableLiveData<Location?>(null)
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            Log.d("LocationService", locationResult.toString())
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
        Log.d("LocationService", "onStartCommand")
        requestLocationUpdates()
        return START_STICKY
    }

    private fun requestLocationUpdates() {
        val fineLocationPermission =
            applicationContext.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        val coarseLocationPermission =
            applicationContext.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)

        if (fineLocationPermission == PackageManager.PERMISSION_GRANTED || coarseLocationPermission == PackageManager.PERMISSION_GRANTED) {
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