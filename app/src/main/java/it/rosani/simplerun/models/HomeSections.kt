package it.rosani.simplerun.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import it.rosani.simplerun.R

enum class HomeSections(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: String
) {
    RUNS_LIST(R.string.home_runs_list, R.drawable.ic_runs_list, "home/your_runs"),
    MAIN(R.string.home_main, R.drawable.ic_running, "home/start"),
    SETTINGS(R.string.home_settings, R.drawable.ic_settings, "home/settings"),
}
