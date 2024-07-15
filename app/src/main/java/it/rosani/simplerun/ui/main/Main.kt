package it.rosani.simplerun.ui.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import it.rosani.simplerun.R
import it.rosani.simplerun.ext.isLocationPermissionGranted
import it.rosani.simplerun.ui.components.RequestLocationModal
import it.rosani.simplerun.ui.components.SimpleRunBottomAppBar
import it.rosani.simplerun.ui.main.runs_list.RunsList
import it.rosani.simplerun.ui.main.settings.Settings
import it.rosani.simplerun.ui.theme.SimplerunTheme
import kotlinx.coroutines.launch
import it.rosani.simplerun.ui.main.map.Map

fun NavGraphBuilder.addHomeGraph(
    onNavigateToRoute: (String) -> Unit,
    upPress: () -> Unit,
    modifier: Modifier = Modifier
) {
    composable(HomeSections.MAIN.route) {
        Main(onNavigateToRoute, upPress, modifier)
    }
    composable(HomeSections.SETTINGS.route) {
        Settings(onNavigateToRoute, upPress, modifier)
    }
    composable(HomeSections.RUNS_LIST.route) {
        RunsList(onNavigateToRoute, upPress, modifier)
    }

}

enum class HomeSections(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: String
) {
    RUNS_LIST(R.string.home_runs_list, R.drawable.ic_runs_list, "home/your_runs"),
    MAIN(R.string.home_main, R.drawable.ic_running, "home/start"),
    SETTINGS(R.string.home_settings, R.drawable.ic_settings, "home/settings"),

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main(
    onNavigateToRoute: (String) -> Unit,
    upPress: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.Hidden,
        skipHiddenState = false
    )

    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            SimpleRunBottomAppBar(
                tabs = HomeSections.entries.toTypedArray(),
                currentRoute = HomeSections.MAIN.route,
                navigateToRoute = onNavigateToRoute
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { innerPadding ->
        Surface(
            modifier = Modifier.padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            Map(modifier = Modifier.fillMaxSize())

            LaunchedEffect(Unit) {
                if (!context.isLocationPermissionGranted()) {
                    sheetState.show()
                }
            }

            if (!context.isLocationPermissionGranted()) {
                RequestLocationModal(
                    onDismissRequest = { scope.launch { sheetState.hide() } },
                    onLocationPermissionGranted = { scope.launch { sheetState.hide() } },
                    sheetState = sheetState
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SimplerunTheme {
        Main(onNavigateToRoute = {}, upPress = {})
    }
}
