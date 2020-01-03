package ru.skillbranch.devintensive.extensions

fun String.truncate(num: Int = 16): String = if (num < this.trimEnd().length)
    this.take(num).trimEnd().plus("...")
    else this.trimEnd()

fun String.stripHtml(): String {
    var result = this.replace(Regex(" +"), " ").
        replace(Regex("<.*?>"), "")
    return result
}
