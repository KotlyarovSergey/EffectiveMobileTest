package com.ksv.effectivemobiletest.presentation.home

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ksv.effectivemobiletest.data.Repository
import com.ksv.effectivemobiletest.entity.CourseItem

class CoursePagingSource: PagingSource<Int, CourseItem>() {
    private val repository = Repository()

    override fun getRefreshKey(state: PagingState<Int, CourseItem>): Int = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CourseItem> {
        val page = params.key ?: FIRST_PAGE

        return kotlin.runCatching {
            repository.getPagedCourse(page)
        }.fold(
            onSuccess = { result ->
                Log.d("ksvlog", "onSuccess. pg: $page")

                LoadResult.Page(
                    data = result?.courses?.map { it.toCourseItem() } ?: emptyList(),
                    prevKey = if (result?.meta?.hasPrevious == true) page - 1 else null,
                    nextKey = if (result?.meta?.hasNext == true) page + 1 else null
                )
            },
            onFailure = {
                Log.d("ksvlog", "onFailure")
                LoadResult.Error(it)
            })
    }

    companion object {
        const val FIRST_PAGE = 1
    }
}