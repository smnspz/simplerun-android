package it.rosani.simplerun.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import it.rosani.simplerun.models.HomeSections

@Composable
fun SimpleRunScaffold(
    modifier: Modifier = Modifier,
    currentRoute: HomeSections,
    navigateToRoute: (String) -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            SimpleRunBottomAppBar(
                tabs = HomeSections.entries.toTypedArray(),
                currentRoute = currentRoute.route,
                navigateToRoute = navigateToRoute
            )
        },
        content = { padding ->
            content(padding)
        }
    )
}