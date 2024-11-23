package com.ksv.effectivemobiletest.data.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Meta(
    @Json(name = "page") val page: Int,
    @Json(name = "has_next") val hasNext: Boolean,
    @Json(name = "has_previous") val hasPrevious: Boolean
)