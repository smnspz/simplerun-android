package it.rosani.simplerun.ui.components

import android.graphics.drawable.Drawable
import android.location.Location
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.utsman.osmandcompose.MapProperties
import com.utsman.osmandcompose.Marker
import com.utsman.osmandcompose.OpenStreetMap
import com.utsman.osmandcompose.ZoomButtonVisibility
import com.utsman.osmandcompose.rememberCameraState
import com.utsman.osmandcompose.rememberMarkerState
import it.rosani.simplerun.R
import it.rosani.simplerun.ui.MainActivity
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint

@Composable
fun Map(modifier: Modifier = Modifier, location: State<Location?>) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        (context as MainActivity).setupMap()
    }

    val mapProperties by remember {
        mutableStateOf(
            MapProperties(
                minZoomLevel = 19.9,
                zoomButtonVisibility = ZoomButtonVisibility.NEVER,
                tileSources = TileSourceFactory.MAPNIK
            )
        )
    }

    val markerIcon: Drawable? by remember {
        mutableStateOf(ContextCompat.getDrawable(context, R.drawable.ic_marker))
    }

    val markerState = rememberMarkerState(
        geoPoint = GeoPoint(45.4685, 9.1824)
    )

    val cameraState = rememberCameraState {
        geoPoint = GeoPoint(45.4685, 9.1824)
        zoom = 19.9
    }

    OpenStreetMap(
        modifier = modifier,
        cameraState = cameraState,
        properties = mapProperties,
    ) {
        if (location.value != null) {
            Marker(
                state = markerState,
                icon = markerIcon
            )
        }
    }

    LaunchedEffect(location.value) {
        location.value?.let {
            val geoPoint = GeoPoint(it.latitude, it.longitude)
            markerState.geoPoint = geoPoint
            cameraState.animateTo(geoPoint)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            (context as MainActivity).clearMap()
        }
    }
}


