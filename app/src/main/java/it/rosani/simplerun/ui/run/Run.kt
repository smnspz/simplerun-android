package it.rosani.simplerun.ui.run

import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import it.rosani.simplerun.R
import it.rosani.simplerun.ext.formatTimeForTimer
import it.rosani.simplerun.ui.components.AutoResizeText
import it.rosani.simplerun.ui.components.SimpleRunActionButton
import it.rosani.simplerun.ui.navigation.MainDestinations
import it.rosani.simplerun.viewmodels.RunViewModel

fun NavGraphBuilder.addRunGraph(
    onNavigateToRoute: (String) -> Unit,
    upPress: () -> Unit,
    modifier: Modifier = Modifier
) {
    composable(
        route = MainDestinations.RUN.route,
        enterTransition = { slideInVertically(initialOffsetY = { it }) },
        exitTransition = { slideOutVertically(targetOffsetY = { it }) },
    ) {
        Run(modifier, onNavigateToRoute, upPress)
    }
}

@Composable
fun Run(
    modifier: Modifier = Modifier,
    onNavigateToRoute: (String) -> Unit,
    upPress: () -> Unit,
    viewModel: RunViewModel = viewModel()
) {
    val isRunning by viewModel.isRunning.collectAsState()
    val elapsedTime by viewModel.elapsedTime.collectAsState()

    BackHandler(enabled = true) {}

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 8.dp)
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                RunRow(
                    modifier = Modifier
                        .weight(1f),
                    upperText = R.string.run_time
                ) {
                    elapsedTime.formatTimeForTimer()
                }
                HorizontalDivider(Modifier.padding(16.dp))

                RunRow(
                    modifier = Modifier.weight(1.5f),
                    upperText = R.string.run_avg_pace,
                    lowerText = R.string.run_avg_pace_unit,
                ) {
                    "0:00"
                }

                HorizontalDivider(Modifier.padding(16.dp))
                RunRow(
                    modifier = Modifier.weight(1f),
                    upperText = R.string.run_distance,
                    lowerText = R.string.run_distance_unit,
                ) {
                    "0,00"
                }

                Row(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    SimpleRunActionButton(
                        textColor = Color.White,
                        circleColor = Color.Red,
                        text = if (isRunning) R.string.run_pause else R.string.run_resume,
                        modifier = Modifier.clickable {
                            viewModel.toggleRunning()
                        }
                    )
                    if (!isRunning) {
                        Spacer(modifier = Modifier.width(16.dp))
                        SimpleRunActionButton(
                            textColor = Color.White,
                            circleColor = Color.Black,
                            text = R.string.run_stop,
                            modifier = Modifier.clickable {
                                upPress()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RunRow(
    modifier: Modifier = Modifier,
    @StringRes upperText: Int,
    @StringRes lowerText: Int? = null,
    updateValue: () -> String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column {
            Text(
                text = stringResource(upperText),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                AutoResizeText(
                    text = updateValue(),
                    textStyle = TextStyle(
                        fontSize = 100.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    ),
                )
            }
            lowerText?.let {
                Text(
                    text = stringResource(lowerText),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RunPreview() {
    Run(onNavigateToRoute = {}, upPress = {})
}