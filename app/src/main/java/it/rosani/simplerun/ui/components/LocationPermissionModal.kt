package it.rosani.simplerun.ui.components

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import it.rosani.simplerun.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestLocationModal(
    onDismissRequest: () -> Unit,
    onLocationPermissionGranted: () -> Unit,
    sheetState: SheetState,
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

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState
    ) {
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
                    text = "I accept location permissions",
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = "I just want to take a look around",
                modifier = Modifier
                    .padding(top = 12.dp)
                    .clickable { onDismissRequest() },
                fontSize = 12.sp,
                textDecoration = TextDecoration.Underline,
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun RequestLocationModalPreview() {
    RequestLocationModal(
        onDismissRequest = {},
        onLocationPermissionGranted = {},
        sheetState = rememberStandardBottomSheetState()
    )
}
