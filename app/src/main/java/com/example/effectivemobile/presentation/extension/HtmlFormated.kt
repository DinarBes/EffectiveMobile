package com.example.effectivemobile.presentation.extension

import androidx.compose.ui.text.buildAnnotatedString
import androidx.core.text.HtmlCompat

fun htmlFormated(text: String): String {

    return buildAnnotatedString {
        append(HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY).toString())
    }.toString()
}