package com.ksv.effectivemobiletest.data

import com.ksv.effectivemobiletest.data.entities.CourseDataResponse
import com.ksv.effectivemobiletest.data.entities.CoursesListResponse
import com.ksv.effectivemobiletest.data.entities.ReviewSummaryResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://stepik.org/api/"

object RetrofitInstance {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val getApi: GetCourseApi = retrofit.create(
        GetCourseApi::class.java
    )
}

interface GetCourseApi {
    @GET("courses")
    suspend fun getCourseResponse(
        @Query("ids[]") id: Int
    ): Response<CourseDataResponse>

    @GET("courses")
    suspend fun getCoursesByTag(
        @Query("tag") tag: Int
    ): Response<CourseDataResponse>

    @GET("course-lists")
    suspend fun getCoursesList(
        @Query("ids[]") listId: Int
    ): Response<CoursesListResponse>

    @GET("course-review-summaries")
    suspend fun getCourseReviewSummary(
        @Query("ids[]") reviewSummaryId: Int
    ): Response<ReviewSummaryResponse>


    @GET("courses?tag=55")
    suspend fun getCourses(): Response<CourseDataResponse>

    @GET("courses")
    suspend fun coursesPaged(@Query("page") page: Int): Response<CourseDataResponse>
}

