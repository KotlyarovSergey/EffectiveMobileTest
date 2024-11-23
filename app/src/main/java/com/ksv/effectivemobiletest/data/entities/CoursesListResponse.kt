package com.ksv.effectivemobiletest.data.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CoursesListResponse(
    @Json(name = "meta") val meta: Meta,
    @Json(name = "course-lists") val courseLists: List<CourseLists>
)

@JsonClass(generateAdapter = true)
data class CourseLists(
    @Json(name = "courses") val courses: List<Int>
)