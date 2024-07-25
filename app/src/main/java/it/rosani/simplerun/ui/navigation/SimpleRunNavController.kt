package it.rosani.simplerun.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

enum class MainDestinations(val route: String) {
    HOME("home"),
    RUN("run")
}

@Composable
fun rememberSimpleRunNavController(
    navController: NavHostController = rememberNavController()
): SimpleRunNavController = remember(navController) {
    SimpleRunNavController(navController)
}

class SimpleRunNavController(val navController: NavHostController) {
    val currentRoute: String?
        get() = navController.currentDestination?.route

    fun upPress() {
        navController.navigateUp()
    }

    fun navigateToRoute(route: String) {
        if (route != currentRoute) {
            navController.navigate(route) {
                launchSingleTop = true
                restoreState = true
            }
        }
    }

}