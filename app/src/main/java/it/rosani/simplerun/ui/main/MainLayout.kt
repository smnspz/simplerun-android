package it.rosani.simplerun.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import it.rosani.simplerun.ui.components.StartButton

@Composable
fun MainLayout(modifier: Modifier = Modifier, bottomBarHeight: Dp) {
    Layout(
        modifier = modifier,
        content = {
            Map()
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background),
                    contentAlignment = Alignment.Center // Center the content of this box
                ) {
                    StartButton(modifier = Modifier.padding(vertical = 16.dp))
                }
            }
        }
    ) { measurables, constraints ->
        val mapMeasurable = measurables[0]
        val boxMeasurable = measurables[1]

        val boxPlaceable = boxMeasurable.measure(constraints.copy(maxHeight = Constraints.Infinity))
        val remainingHeight =
            constraints.maxHeight - boxPlaceable.height - bottomBarHeight.roundToPx()

        val mapPlaceable = mapMeasurable.measure(constraints.copy(maxHeight = remainingHeight))

        layout(constraints.maxWidth, constraints.maxHeight) {
            mapPlaceable.placeRelative(0, 0)
            boxPlaceable.placeRelative(0, remainingHeight)
        }
    }
}