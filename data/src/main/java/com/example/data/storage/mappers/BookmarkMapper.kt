package com.example.data.storage.mappers

import com.example.data.storage.local.BookmarkEntity
import com.example.domain.models.Bookmark
import com.example.domain.models.Course

fun Course.toBookmarkEntity(): BookmarkEntity {
    return BookmarkEntity(
        id = id,
        summary = summary,
        cover = cover,
        description = description,
        price = price,
        title = title,
        createDate = createDate,
    )
}

fun BookmarkEntity.toCourse(): Course {
    return Course(
        id = id,
        summary = summary,
        cover = cover,
        description = description,
        price = price,
        title = title,
        createDate = createDate,
    )
}