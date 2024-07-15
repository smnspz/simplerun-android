package it.rosani.simplerun.ui.main.settings

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import it.rosani.simplerun.ui.components.SimpleRunBottomAppBar
import it.rosani.simplerun.ui.main.HomeSections

@Composable
fun Settings(
    navigateToRoute: (String) -> Unit,
    upPress: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        bottomBar = {
            SimpleRunBottomAppBar(
                tabs = HomeSections.entries.toTypedArray(),
                currentRoute = HomeSections.RUNS_LIST.route,
                navigateToRoute = navigateToRoute
            )
        }
    ){
        Text(text = "Settings", modifier = modifier.padding(it))
    }
}