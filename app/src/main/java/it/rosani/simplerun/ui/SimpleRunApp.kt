package it.rosani.simplerun.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.navigation
import it.rosani.simplerun.models.HomeSections
import it.rosani.simplerun.ui.main.addHomeGraph
import it.rosani.simplerun.ui.run.addRunGraph
import it.rosani.simplerun.ui.navigation.MainDestinations
import it.rosani.simplerun.ui.navigation.rememberSimpleRunNavController
import it.rosani.simplerun.ui.theme.SimplerunTheme
import it.rosani.simplerun.viewmodels.MainViewModel

@Composable
fun SimpleRunApp(viewModel: MainViewModel) {
    SimplerunTheme {
        val simpleRunNavController = rememberSimpleRunNavController()
        NavHost(
            navController = simpleRunNavController.navController,
            startDestination = MainDestinations.HOME.route
        ) {
            simpleRunNavGraph(
                onNavigateToRoute = simpleRunNavController::navigateToRoute,
                upPress = simpleRunNavController::upPress,
                viewModel = viewModel
            )
        }
    }
}

private fun NavGraphBuilder.simpleRunNavGraph(
    onNavigateToRoute: (String) -> Unit,
    upPress: () -> Unit,
    viewModel: MainViewModel
) {
    navigation(
        route = MainDestinations.HOME.route,
        startDestination = HomeSections.RUNS_LIST.route
    ) {
        addHomeGraph(onNavigateToRoute, upPress, viewModel)
        addRunGraph(onNavigateToRoute, upPress)
    }
}
