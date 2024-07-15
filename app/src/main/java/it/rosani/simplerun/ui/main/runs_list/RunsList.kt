package it.rosani.simplerun.ui.main.runs_list

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import it.rosani.simplerun.ui.components.SimpleRunBottomAppBar
import it.rosani.simplerun.ui.main.HomeSections

@Composable
fun RunsList(
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
        Text(text = "Runs list", modifier = modifier.padding(it))
    }
}