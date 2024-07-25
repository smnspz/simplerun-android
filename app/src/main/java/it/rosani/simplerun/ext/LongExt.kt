package it.rosani.simplerun.ext

import java.util.Locale
import kotlin.time.Duration.Companion.milliseconds

fun Long.formatTimeForTimer(): String {
    val duration = this.milliseconds
    val hours = duration.inWholeHours
    val minutes = duration.inWholeMinutes % 60
    val seconds = duration.inWholeSeconds % 60

    return String.format(
        Locale.getDefault(),
        "%02d:%02d:%02d",
        hours, minutes, seconds
    )
}
