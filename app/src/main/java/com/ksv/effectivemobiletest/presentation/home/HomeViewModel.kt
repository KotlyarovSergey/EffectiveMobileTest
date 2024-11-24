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

    init {
        val repo = Repository()
        viewModelScope.launch {
            coursesIdsList = repo.getNewestCoursesIdsList()
            coursesIdsList.forEach { id ->
                val course = repo.getCourseAtId(id)
                if(course != null) {
                    _courses.update {
                        _courses.value.toMutableList().apply { this.add(course) }
                    }
                }
            }
        }
    }


}