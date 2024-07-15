package it.rosani.simplerun.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import it.rosani.simplerun.BuildConfig
import org.osmdroid.config.Configuration
import java.io.File


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupMap()
        enableEdgeToEdge()
        setContent {
            SimpleRunApp()
        }
    }

    private fun setupMap() {
        try {
            Configuration.getInstance().apply {
                load(
                    applicationContext,
                    applicationContext.getSharedPreferences("osmdroid", MODE_PRIVATE)
                )
                userAgentValue = BuildConfig.APPLICATION_ID
                osmdroidBasePath = applicationContext.filesDir
                osmdroidTileCache = File(osmdroidBasePath, "tile")
            }
            Log.d("MainActivity", "Map setup complete")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("MainActivity", "Error while setting up map", e)
        }
    }
}
