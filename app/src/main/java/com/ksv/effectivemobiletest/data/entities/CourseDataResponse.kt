package com.ksv.effectivemobiletest.data.entities

import com.ksv.effectivemobiletest.entity.CourseItem
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass



@JsonClass(generateAdapter = true)
data class CourseDataResponse(
    @Json(name = "meta") val meta: Meta,
    @Json(name = "courses") val courses: List<Courses>
)

@JsonClass(generateAdapter = true)
data class Courses(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "update_date") val date: String,
    @Json(name = "summary") val summary: String,
    @Json(name = "price") val price: Double?,
    @Json(name = "cover") val cover: String,
    @Json(name = "canonical_url") val url: String,
    @Json(name = "review_summary") val reviewSummary: Int

){
    fun toCourseItem() = CourseItem(
        id = id,
        cost = price?.toInt() ?: 0,
        date = date,
        link = url,
        name = title,
        img = cover,
        description = summary
    )
}

