package it.rosani.simplerun.ui.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import it.rosani.simplerun.R
import it.rosani.simplerun.ui.components.SimpleRunBottomAppBar
import it.rosani.simplerun.ui.components.SimpleRunBottomSheetScaffold
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
        Scaffold(
            bottomBar = {
                SimpleRunBottomAppBar(
                    modifier = Modifier.padding(innerPadding),
                    tabs = HomeSections.entries.toTypedArray(),
                    currentRoute = HomeSections.MAIN.route,
                    navigateToRoute = onNavigateToRoute
                )
            },
            contentWindowInsets = WindowInsets(0, 0, 0, 0)
        ) { scaffoldPadding ->
            Box(modifier = Modifier.fillMaxSize()) {
                Surface(
                    modifier = modifier
                        .padding(scaffoldPadding)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            enabled = bottomSheetState.currentValue == SheetValue.Expanded,
                        ) {
                            dismissSheet()
                        },
                    color = MaterialTheme.colorScheme.background
                ) {
                    Map(modifier = Modifier.apply {
                        fillMaxSize()
                    })
                }

                // Overlay to hide the bottom sheet when the user clicks on the map
                AnimatedVisibility(
                    visible = bottomSheetState.currentValue == SheetValue.Expanded,
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable(
                                enabled = true,
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) { dismissSheet() }
                            .alpha(0.7f),
                        color = Color.DarkGray,
                    ) {}
                }
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
