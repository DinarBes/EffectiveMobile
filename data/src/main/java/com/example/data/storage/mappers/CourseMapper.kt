package com.example.data.storage.mappers

import com.example.data.storage.local.CourseEntity
import com.example.domain.models.Course

fun Course.toCourseEntity(): CourseEntity {
    return CourseEntity(
        id = id,
        summary = summary,
        cover = cover,
        description = description,
        totalUnits = totalUnits ?: 0,
        lessonsCount = lessonsCount ?: 0,
        price = price,
        title = title,
        createDate = createDate,
        page = page ?: 2
    )
}

fun CourseEntity.toCourse(): Course {
    return Course(
        id = id,
        summary = summary,
        cover = cover,
        description = description,
        totalUnits = totalUnits,
        lessonsCount = lessonsCount,
        price = price,
        title = title,
        createDate = createDate,
        page = page
    )
}