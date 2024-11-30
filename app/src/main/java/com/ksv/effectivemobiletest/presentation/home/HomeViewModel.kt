package com.ksv.effectivemobiletest.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ksv.effectivemobiletest.data.Repository
import com.ksv.effectivemobiletest.entity.CourseItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private var coursesIdsList = listOf<Int>()
    private val _courses = MutableStateFlow<MutableList<CourseItem>>(mutableListOf())
    val courses = _courses.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    private var sortingBy = SortingTypes.DATE_DOWN

    init {
        loadData()
    }

    private fun loadData() {
        val repo = Repository()
        viewModelScope.launch {
            _isLoading.value = true
            coursesIdsList = repo.getNewestCoursesIdsList().subList(0, 20)
            if (coursesIdsList.isNotEmpty()) {
                coursesIdsList.forEach { id ->
                    val course = repo.getCourseAtId(id)
                    _isLoading.value = false
                    if (course != null) {
                        _courses.update {
                            _courses.value.toMutableList().apply {
                                this.add(course)
                                sort(this)
                            }
                        }
                        //Log.d("ksvlog", "id:${course.id}: dt: ${course.date}")
                    }
                }
            } else {
                _isLoading.value = false
            }
        }
    }

    private fun sort(list: MutableList<CourseItem>) {
        when (sortingBy) {
            SortingTypes.DATE_DOWN -> list.sortBy { it.date }
            SortingTypes.DATE_UP -> list.sortByDescending { it.date }
            SortingTypes.RATING_UP -> list.sortBy { it.rating }
            SortingTypes.RATING_DOWN -> list.sortByDescending { it.rating }
            SortingTypes.POPULARITY_UP -> list.sortBy { it.learners }
            SortingTypes.POPULARITY_DOWN -> list.sortByDescending { it.learners }
            else -> {}
        }
    }

    private fun sortCourses() {
        _courses.update {
            _courses.value.toMutableList().apply {
                sort(this)
            }
        }
    }

    fun changeSort() {
        val nextOrdinal = (sortingBy.ordinal + 1) % SortingTypes.entries.size
        val newSorting = SortingTypes.entries.toTypedArray()[nextOrdinal]
        sortingBy = newSorting
        Log.d("ksvlog", "sortBy $sortingBy")
        sortCourses()
    }

}