package it.rosani.simplerun.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import it.rosani.simplerun.ui.components.StartButton

@Composable
fun MainBottomAppBar(modifier: Modifier = Modifier) {
    Surface(
        modifier.background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .windowInsetsPadding(WindowInsets.navigationBars),
            contentAlignment = Alignment.Center
        ) {
            StartButton(modifier = Modifier.padding(vertical = 16.dp))
        }
    }
}

@Preview
@Composable
private fun MainBottomAppBarPreview() {
    MainBottomAppBar()
}