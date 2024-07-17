package it.rosani.simplerun.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.utsman.osmandcompose.MapProperties
import com.utsman.osmandcompose.OpenStreetMap
import com.utsman.osmandcompose.ZoomButtonVisibility
import com.utsman.osmandcompose.rememberCameraState
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint

@Composable
fun Map(modifier: Modifier = Modifier) {
    var mapProperties by remember { mutableStateOf(MapProperties()) }

    SideEffect {
        mapProperties = mapProperties
            .copy(zoomButtonVisibility = ZoomButtonVisibility.NEVER)
            .copy(tileSources = TileSourceFactory.MAPNIK)
    }

    val cameraState = rememberCameraState {
        geoPoint = GeoPoint(51.5074, -0.1278)
        zoom = 19.9
    }

    OpenStreetMap(
        modifier = modifier,
        cameraState = cameraState,
        properties = mapProperties,
    )
}