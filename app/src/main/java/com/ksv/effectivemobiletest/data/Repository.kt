package com.ksv.effectivemobiletest.data

import android.util.Log
import com.ksv.effectivemobiletest.entity.CourseItem

class Repository {

//    suspend fun getNewestCourses(): List<CourseItem>{
//        return getCoursesAtList(1)
//    }

    suspend fun getNewestCoursesIdsList(): List<Int> {
        return getCoursesIdsAtList(1)
    }

    private suspend fun getCoursesIdsAtList(listId: Int): List<Int> {
        return try {
            val response = RetrofitInstance.getApi.getCoursesList(listId)
            if (response.isSuccessful) {
                val coursesListResponse = response.body()
                if (coursesListResponse != null) {
                    val listOfCoursesIds = coursesListResponse.courseLists.first().courses
                    listOfCoursesIds
                } else {
                    emptyList()
                }
            } else {
                emptyList()
            }
        } catch (exception: Exception) {
            Log.d(
                "ksvlog",
                "Repository.getCoursesIdsAtList -> listId: $listId \n${exception.message}"
            )
            emptyList()
        }

    }

    suspend fun getCourseAtId(courseId: Int): CourseItem? {
        return try {
            val response = RetrofitInstance.getApi.getCourseResponse(courseId)
            if (response.isSuccessful) {
                val courseData = response.body()
                if (courseData != null) {
                    val course = courseData.courses.first()
                    //Log.d("ksvlog", "id: ${course.id} price: ${course.price}")
                    val reviewId = course.reviewSummary
                    val rating = getRating(reviewId)
                    val courseItem = CourseItem(
                        id = course.id,
                        name = course.title,
                        date = course.date,             // !!!!!!!!!! исправитжь
                        cost = course.price?.toInt() ?: 0,    // !!!!!!!!! исправить
                        link = course.url,
                        img = course.cover,
                        description = course.summary,
                        rating = rating ?: 0.0
                    )
                    courseItem
                } else {
                    null
                }
            } else {
                null
            }
        } catch (exception: Exception) {
            Log.d(
                "ksvlog",
                "Repository.getCoursesFromIdsList -> courseId: $courseId \n${exception.message}"
            )
            null
        }

        return null
    }


    private suspend fun getCoursesAtList(listId: Int): List<CourseItem> {
        return try {
            val response = RetrofitInstance.getApi.getCoursesList(listId)
            if (response.isSuccessful) {
                val coursesListResponse = response.body()
                if (coursesListResponse != null) {
                    val listOfCoursesIds = coursesListResponse.courseLists.first().courses
                    val coursesList = getCoursesFromIdsList(listOfCoursesIds)
                    coursesList
                } else {
                    emptyList()
                }
            } else {
                emptyList()
            }
        } catch (exception: Exception) {
            Log.d("ksvlog", "Repository.getCoursesAtList -> listId: $listId \n${exception.message}")
            emptyList()
        }

    }

    private suspend fun getCoursesFromIdsList(idsList: List<Int>): List<CourseItem> {
        // для каждого Idкурса вытянуть сам курс
        // потом для каждого курса вытянуть рейтинг
        val coursesList = mutableListOf<CourseItem>()
        idsList.forEach { courseId ->
            try {
                val response = RetrofitInstance.getApi.getCourseResponse(courseId)
                if (response.isSuccessful) {
                    val courseData = response.body()
                    if (courseData != null) {
                        val course = courseData.courses.first()
                        //Log.d("ksvlog", "id: ${course.id} price: ${course.price}")
                        val reviewId = course.reviewSummary
                        val rating = getRating(reviewId)
                        val courseItem = CourseItem(
                            id = course.id,
                            name = course.title,
                            date = course.date,             // !!!!!!!!!! исправитжь
                            cost = course.price?.toInt() ?: 0,    // !!!!!!!!! исправить
                            link = course.url,
                            img = course.cover,
                            description = course.summary,
                            rating = rating ?: 0.0
                        )
                        coursesList.add(courseItem)
                    }
                }
            } catch (exception: Exception) {
                Log.d(
                    "ksvlog",
                    "Repository.getCoursesFromIdsList -> courseId: $courseId \n${exception.message}"
                )
            }
        }

        return coursesList
    }

    private suspend fun getRating(reviewId: Int): Double? {

        return null
    }
}