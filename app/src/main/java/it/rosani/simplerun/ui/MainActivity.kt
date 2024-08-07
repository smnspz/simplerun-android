package it.rosani.simplerun.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import it.rosani.simplerun.BuildConfig
import it.rosani.simplerun.ext.isLocationPermissionGranted
import it.rosani.simplerun.services.location_service.LocationService
import it.rosani.simplerun.viewmodels.MainViewModel
import org.osmdroid.config.Configuration
import java.io.File


class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.bindLocationService()
        enableEdgeToEdge()
        if (isLocationPermissionGranted()) {
            startService(Intent(this, LocationService::class.java))
        }
        setContent {
            SimpleRunApp(viewModel)
        }
    }

    fun setupMap() {
        Configuration.getInstance().apply {
            load(
                applicationContext,
                applicationContext.getSharedPreferences("osmdroid", MODE_PRIVATE)
            )
            userAgentValue = BuildConfig.APPLICATION_ID
            osmdroidBasePath = applicationContext.filesDir
            osmdroidTileCache = File(osmdroidBasePath, "tile")
        }
    }

    fun clearMap() {
        Configuration.getInstance().osmdroidTileCache.delete()
    }

    fun startLocationService() {
        startService(Intent(this, LocationService::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.unbindLocationService()
    }
}
