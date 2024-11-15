package com.example.data.storage.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CourseModel(
    val meta: Meta,
    val courses: List<Courses>
)

@Serializable
data class Meta(
    val page: Int,
    @SerialName("has_next")
    val hasNext: Boolean,
    @SerialName("has_previous")
    val hasPrevious: Boolean
)

@Serializable
data class Courses(
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
    val createDate: String
)