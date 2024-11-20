package com.example.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.math.roundToInt
import kotlin.random.Random

@Serializable
data class Course(
    val id: Int,
    val summary: String,
    val cover: String,
    val description: String,
    @SerialName("total_units")
    val totalUnits: Int? = null,
    @SerialName("lessons_count")
    val lessonsCount: Int? = null,
    val price: String? = null,
    val title: String,
    @SerialName("create_date")
    val createDate: String,
    val page: Int? = null,
    val rating: Double = ((Random.nextDouble(1.0, 5.0) * 10).roundToInt() / 10.0)
)