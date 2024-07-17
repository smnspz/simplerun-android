package it.rosani.simplerun.ui.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import it.rosani.simplerun.models.HomeSections
import it.rosani.simplerun.ui.components.SimpleRunScaffold

@Composable
fun Settings(
    navigateToRoute: (String) -> Unit,
    upPress: () -> Unit,
    modifier: Modifier = Modifier
) {
    SimpleRunScaffold(
        currentRoute = HomeSections.SETTINGS,
        navigateToRoute = navigateToRoute,
        upPress = upPress,
    ){
        Box(modifier = modifier.padding(it)) {
            Text(text = "Settings")
        }
    }
}