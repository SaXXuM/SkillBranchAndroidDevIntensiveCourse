package ru.skillbranch.devintensive.extensions

import ru.skillbranch.devintensive.utils.Utils.declOfNum
import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = SECOND * 60
const val HOUR = MINUTE * 60
const val DAY = HOUR * 24

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += when(units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
    val timeDiff = date.time - this.time
    return when {
        timeDiff in 0 until 1 * SECOND -> "только что"
        timeDiff in 1 * SECOND until 45 * SECOND -> "несколько секунд назад"
        timeDiff in 45 * SECOND until 75 * SECOND -> "минуту назад"
        timeDiff in 75 * SECOND until 45 * MINUTE ->
            "${timeDiff / MINUTE} ${declOfNum((timeDiff / MINUTE).toInt(), arrayOf("минуту", "минуты", "минут"))} назад"
        timeDiff in 45 * MINUTE until 75 * MINUTE -> "час назад"
        timeDiff in 75 * MINUTE until 22 * HOUR ->
            "${timeDiff / HOUR} ${declOfNum((timeDiff / HOUR).toInt(), arrayOf("час", "часа", "часов"))} назад"
        timeDiff in 22 * HOUR until 26 * HOUR -> "день назад"
        timeDiff in 26 * HOUR until 360 * DAY ->
            "${timeDiff / DAY} ${declOfNum((timeDiff / DAY).toInt(), arrayOf("день", "дня", "дней"))} назад"

        timeDiff in -1 * SECOND until 0 -> "через секунду"
        timeDiff in -45 * SECOND until -1 * SECOND -> "через несколько секунд"
        timeDiff in -75 * SECOND until -45 * SECOND -> "через минуту"
        timeDiff in -45 * MINUTE until -75 * SECOND ->
            "через ${abs(timeDiff) / MINUTE} ${declOfNum((abs(timeDiff) / MINUTE).toInt(), arrayOf("минуту", "минуты", "минут"))}"
        timeDiff in -75 * MINUTE until -45 * MINUTE -> "через час"
        timeDiff in -22 * HOUR until -75 * MINUTE ->
            "через ${abs(timeDiff) / HOUR} ${declOfNum((abs(timeDiff) / HOUR).toInt(), arrayOf("час", "часа", "часов"))}"
        timeDiff in -26 * HOUR until -22 * HOUR -> "через день"
        timeDiff in -360 * DAY until -26 * HOUR ->
            "через ${abs(timeDiff) / DAY} ${declOfNum((abs(timeDiff) / DAY).toInt(), arrayOf("день", "дня", "дней"))}"
        timeDiff < -360 * DAY -> "более чем через год"
        else -> "более года назад"
    }
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY
}