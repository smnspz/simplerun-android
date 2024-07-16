package it.rosani.simplerun.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import it.rosani.simplerun.ext.toSnakeCase
import it.rosani.simplerun.ui.main.HomeSections

@Composable
fun SimpleRunBottomAppBar(
    modifier: Modifier = Modifier,
    tabs: Array<HomeSections>,
    currentRoute: String,
    navigateToRoute: (String) -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        modifier = modifier.windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            tabs.forEach {
                SimpleRunBottomNavigationItem(
                    title = it.title,
                    icon = it.icon,
                    currentRoute = currentRoute,
                    navigateToRoute = navigateToRoute,
                )
            }
        }
    }
}

@Composable
fun SimpleRunBottomNavigationItem(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    @StringRes title: Int,
    currentRoute: String,
    navigateToRoute: (String) -> Unit
) {
    val context = LocalContext.current
    val route = context.getString(title).toSnakeCase()

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .clickable {
                if (currentRoute != "home/$route") {
                    navigateToRoute("home/$route")
                }
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "$title icon",
            modifier = Modifier.size(30.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = LocalContext.current.getString(title),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Preview
@Composable
private fun SimpleRunBottomAppBarPreview() {
    SimpleRunBottomAppBar(
        tabs = HomeSections.entries.toTypedArray(),
        currentRoute = "home/start",
        navigateToRoute = {},
    )
}