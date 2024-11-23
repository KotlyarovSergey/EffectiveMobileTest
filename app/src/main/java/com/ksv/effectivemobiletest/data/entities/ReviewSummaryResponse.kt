package com.ksv.effectivemobiletest.data.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ReviewSummaryResponse(
    @Json(name="meta") val meta: Meta,
    @Json(name="course-review-summaries") val summaries: List<CourseReviewSummaries>
)

@JsonClass(generateAdapter = true)
data class CourseReviewSummaries(
    @Json(name="average") val average: Double
)


