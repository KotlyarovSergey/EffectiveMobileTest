package com.ksv.effectivemobiletest.presentation.home

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
    private val _sortingBy = MutableStateFlow(SortingTypes.DATE_DOWN)
    val sortingBy = _sortingBy.asStateFlow()

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
                                sortListByType(this)
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

    private fun sortListByType(list: MutableList<CourseItem>) {
        when (_sortingBy.value) {
            SortingTypes.DATE_DOWN -> list.sortByDescending { it.date }
            SortingTypes.RATING_DOWN -> list.sortByDescending { it.rating }
            SortingTypes.POPULARITY_DOWN -> list.sortByDescending { it.learners }
            SortingTypes.PRICE_UP -> list.sortBy { it.cost }
            SortingTypes.PRICE_DOWN -> list.sortByDescending { it.cost }
        }
    }

    fun changeSort(newSortingType: SortingTypes) {
        _sortingBy.value = newSortingType
//        Log.d("ksvlog", "sortBy ${sortingBy.value}")
        _courses.update {
            _courses.value.toMutableList().apply {
                sortListByType(this)
            }
        }
    }

}