package it.rosani.simplerun.ext

fun String.toSnakeCase(): String {
    return this.replace(" ", "_").lowercase()
}