package it.rosani.simplerun.ext

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager

fun Context.isLocationPermissionGranted(): Boolean {
    return checkSelfPermission(
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED ||
            checkSelfPermission(
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
}
