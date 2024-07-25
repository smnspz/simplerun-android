package it.rosani.simplerun.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import it.rosani.simplerun.models.HomeSections
import it.rosani.simplerun.ui.main.MainBottomAppBar
import it.rosani.simplerun.ui.main.MainTopAppBar

@Composable
fun SimpleRunScaffold(
    modifier: Modifier = Modifier,
    currentRoute: HomeSections,
    navigateToRoute: (String) -> Unit,
    upPress: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Scaffold(
            modifier = modifier,
            bottomBar = {
                if (currentRoute != HomeSections.MAIN) {
                    SimpleRunBottomAppBar(
                        tabs = HomeSections.entries.toTypedArray(),
                        currentRoute = currentRoute.route,
                        navigateToRoute = navigateToRoute
                    )
                } else {
                    MainBottomAppBar(
                        navigateToRoute = navigateToRoute
                    )
                }
            },
            topBar = {
                if (currentRoute == HomeSections.MAIN) {
                    MainTopAppBar(upPress = upPress)
                }
            },
            content = { padding ->
                content(padding)
            }
        )
    }
}

