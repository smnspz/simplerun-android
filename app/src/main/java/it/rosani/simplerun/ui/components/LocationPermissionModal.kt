package it.rosani.simplerun.ui.components

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import it.rosani.simplerun.R
import it.rosani.simplerun.ext.isLocationPermissionGranted
import kotlinx.coroutines.launch

@Composable
fun RequestLocationModal(
    onDismissRequest: () -> Unit,
    onLocationPermissionGranted: () -> Unit,
    modifier: Modifier = Modifier
) {
    val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            val permissionGranted = permissions.values.reduce { acc, granted -> acc && granted }
            if (permissionGranted) {
                onLocationPermissionGranted()
            } else {
                onDismissRequest()
            }
        }
    )

    Column(
        modifier = modifier.padding(vertical = 8.dp, horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            text = stringResource(R.string.request_location_permission_title)
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Justify,
            text = stringResource(R.string.request_location_permission_text)
        )
        Button(onClick = { locationPermissionLauncher.launch(locationPermissions) }) {
            Text(
                text = stringResource(R.string.request_location_permission_accept_btn),
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            text = stringResource(R.string.request_location_permission_deny_txt),
            modifier = Modifier
                .padding(top = 12.dp)
                .clickable { onDismissRequest() },
            fontSize = 12.sp,
            textDecoration = TextDecoration.Underline,
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleRunBottomSheetScaffold(
    modifier: Modifier = Modifier,
    bottomSheetState: SheetState,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    content: @Composable (PaddingValues) -> Unit
) {
    val context = LocalContext.current


    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        if (!context.isLocationPermissionGranted()) {
            bottomSheetState.expand()
        }
    }

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            RequestLocationModal(
                onDismissRequest = {
                    scope.launch {
                        bottomSheetState.hide()
                    }
                },
                onLocationPermissionGranted = {
                    scope.launch {
                        bottomSheetState.hide()
                    }
                }
            )
        },
        sheetPeekHeight = 0.dp,
        modifier = modifier.fillMaxSize(),
        content = { paddingValues ->
            content(paddingValues)
        }
    )
}

@Composable
fun ModalDismissOverlay(
    modifier: Modifier = Modifier,
    visible: Boolean,
    onDismissRequest: () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        Surface(
            modifier = modifier
                .fillMaxSize()
                .clickable(
                    enabled = true,
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { onDismissRequest() }
                .alpha(0.7f),
            color = Color.DarkGray,
        ) {}
    }
}

@Preview(showBackground = true)
@Composable
private fun RequestLocationModalPreview() {
    RequestLocationModal(
        onDismissRequest = {},
        onLocationPermissionGranted = {},
    )
}
