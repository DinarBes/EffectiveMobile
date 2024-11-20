package com.example.domain.models

import kotlinx.serialization.SerialName

data class Bookmark(
    val id: Int,
    val summary: String,
    val cover: String,
    val description: String,
    val price: String? = null,
    val title: String,
    @SerialName("create_date")
    val createDate: String,
)