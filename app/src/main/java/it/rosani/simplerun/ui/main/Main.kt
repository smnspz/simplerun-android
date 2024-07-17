package it.rosani.simplerun.ui.main

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import it.rosani.simplerun.models.HomeSections
import it.rosani.simplerun.ui.components.ModalDismissOverlay
import it.rosani.simplerun.ui.components.SimpleRunBottomSheetScaffold
import it.rosani.simplerun.ui.components.SimpleRunScaffold
import it.rosani.simplerun.ui.runs_list.RunsList
import it.rosani.simplerun.ui.settings.Settings
import it.rosani.simplerun.ui.theme.SimplerunTheme
import kotlinx.coroutines.launch

fun NavGraphBuilder.addHomeGraph(
    onNavigateToRoute: (String) -> Unit,
    upPress: () -> Unit,
    modifier: Modifier = Modifier
) {
    composable(
        route = HomeSections.MAIN.route,
        enterTransition = { slideInVertically(initialOffsetY = { it }) },
        exitTransition = { slideOutVertically(targetOffsetY = { it }) },
    ) {
        Main(onNavigateToRoute, upPress, modifier)
    }

    composable(
        route = HomeSections.SETTINGS.route,
        enterTransition = { EnterTransition.None},
        exitTransition = { ExitTransition.None},
    ) {
        Settings(onNavigateToRoute, upPress, modifier)
    }

    composable(
        route = HomeSections.RUNS_LIST.route,
        enterTransition = { EnterTransition.None},
        exitTransition = { ExitTransition.None},
    ) {
        RunsList(onNavigateToRoute, upPress, modifier)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main(
    onNavigateToRoute: (String) -> Unit,
    upPress: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()

    var showLocationPermissionRequest by remember { mutableStateOf(false) }

    val bottomSheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.Hidden,
        skipHiddenState = false
    )

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = bottomSheetState
    )

    val dismissSheet: () -> Unit = {
        scope.launch {
            bottomSheetState.hide()
            showLocationPermissionRequest = false
        }
    }

    SimpleRunBottomSheetScaffold(
        bottomSheetState = bottomSheetState,
        bottomSheetScaffoldState = bottomSheetScaffoldState,
    ) { innerPadding ->
        SimpleRunScaffold(
            currentRoute = HomeSections.MAIN,
            navigateToRoute = onNavigateToRoute,
            upPress = upPress
        ) { paddingValues ->
            Map()

            // Overlay to hide the bottom sheet when the user clicks on the map
            ModalDismissOverlay(
                visible = bottomSheetState.currentValue == SheetValue.Expanded
            ) { dismissSheet() }
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
