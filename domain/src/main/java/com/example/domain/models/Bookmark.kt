package com.example.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class Bookmark(
    val id: Int,
    val summary: String,
    val cover: String,
    val description: String,
    val price: Int? = null,
    val title: String,
    @SerialName("create_date")
    val createDate: String,
)