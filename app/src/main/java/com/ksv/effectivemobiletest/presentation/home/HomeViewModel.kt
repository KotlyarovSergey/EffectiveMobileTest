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


    init {
        loadData()
    }

    private fun loadData() {
        Log.d("ksvlog", "vm: loadData")
        val repo = Repository()
        viewModelScope.launch {
            _isLoading.value = true
            coursesIdsList = repo.getNewestCoursesIdsList()
            Log.d("ksvlog", "vm: coursesList received")
            if (coursesIdsList.isNotEmpty()) {
                coursesIdsList.forEach { id ->
                    val course = repo.getCourseAtId(id)
                    _isLoading.value = false
                    if (course != null) {
                        _courses.update {
                            _courses.value.toMutableList().apply { this.add(course) }
                        }
                        Log.d("ksvlog", "vm: course added to list. sz: ${_courses.value.size}")
                    }
                }
            } else {
                _isLoading.value = false
            }
        }
    }

}