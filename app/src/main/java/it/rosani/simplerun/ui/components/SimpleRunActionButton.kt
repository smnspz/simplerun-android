package it.rosani.simplerun.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SimpleRunActionButton(
    modifier: Modifier = Modifier,
    circleColor: Color = Color.Black,
    textColor: Color = Color.White,
    @StringRes text: Int 
) {
    Box(
        modifier = modifier.size(80.dp), // Adjust size as needed
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            drawCircle(
                color = circleColor,
                radius = size.minDimension / 2
            )
        }
        Text(
            text = stringResource(text),
            color = textColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}