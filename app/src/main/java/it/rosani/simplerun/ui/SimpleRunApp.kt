package it.rosani.simplerun.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.navigation
import it.rosani.simplerun.ui.main.HomeSections
import it.rosani.simplerun.ui.main.addHomeGraph
import it.rosani.simplerun.ui.navigation.MainDestinations
import it.rosani.simplerun.ui.navigation.rememberSimpleRunNavController
import it.rosani.simplerun.ui.theme.SimplerunTheme

@Composable
fun SimpleRunApp() {
    SimplerunTheme {
        val simpleRunNavController = rememberSimpleRunNavController()
        NavHost(
            navController = simpleRunNavController.navController,
            startDestination = MainDestinations.HOME.route
        ) {
            simpleRunNavGraph(
                onNavigateToRoute = simpleRunNavController::navigateToRoute,
                upPress = simpleRunNavController::upPress,
            )
        }
    }
}

private fun NavGraphBuilder.simpleRunNavGraph(
    onNavigateToRoute: (String) -> Unit,
    upPress: () -> Unit,
) {
    navigation(
        route = MainDestinations.HOME.route,
        startDestination = HomeSections.MAIN.route
    ) {
        addHomeGraph(onNavigateToRoute, upPress)
    }
}
