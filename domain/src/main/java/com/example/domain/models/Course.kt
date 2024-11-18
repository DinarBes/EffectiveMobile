package com.example.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Course(
    val id: Int,
    val summary: String,
    val cover: String,
    val description: String,
    @SerialName("total_units")
    val totalUnits: Int,
    @SerialName("lessons_count")
    val lessonsCount: Int,
    val price: Int? = null,
    val title: String,
    @SerialName("create_date")
    val createDate: String,
    val page: Int
)