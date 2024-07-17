package it.rosani.simplerun.ui.runs_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import it.rosani.simplerun.models.HomeSections
import it.rosani.simplerun.ui.components.SimpleRunScaffold

@Composable
fun RunsList(
    navigateToRoute: (String) -> Unit,
    upPress: () -> Unit,
    modifier: Modifier = Modifier
) {
    SimpleRunScaffold(
        currentRoute = HomeSections.RUNS_LIST,
        navigateToRoute = navigateToRoute,
        upPress = upPress,
    ) {
        Box(modifier = modifier.padding(it)) {
            Text(text = "Runs list")
        }
    }
}