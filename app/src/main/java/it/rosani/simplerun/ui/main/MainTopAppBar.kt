package it.rosani.simplerun.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MainTopAppBar(modifier: Modifier = Modifier, upPress: () -> Unit) {
    Surface(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .windowInsetsPadding(WindowInsets.statusBars),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Close",
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clickable(
                        onClick = upPress,
                    ),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SimpleRunTopAppBarPreview() {
    MainTopAppBar(
        upPress = {},
    )
}
