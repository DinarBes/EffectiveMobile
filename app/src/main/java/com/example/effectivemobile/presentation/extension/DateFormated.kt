package com.example.effectivemobile.presentation.extension

import java.text.SimpleDateFormat
import java.util.Locale

fun String.dateToString(fromFormat: String, toFormat: String): String {

    val timeBonuses = SimpleDateFormat(fromFormat, Locale.getDefault()).parse(this)

    val dateFormatter = android.icu.text.SimpleDateFormat(toFormat, Locale.getDefault())

    return dateFormatter.format(timeBonuses)
}