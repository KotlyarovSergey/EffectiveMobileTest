package com.ksv.effectivemobiletest.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ksv.effectivemobiletest.databinding.CourseCardViewWideBinding
import com.ksv.effectivemobiletest.entity.CourseItem
import com.ksv.effectivemobiletest.utils.DateConverter

class CoursesPagedListAdapter(
    private val onDetailsClick: (course: CourseItem) -> Unit
): PagingDataAdapter<CourseItem, CoursesPagedListAdapter.CourseViewHolder>(DiffUtilCallback()) {

    inner class CourseViewHolder(val binding: CourseCardViewWideBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val courseItem = getItem(position)
        holder.binding.courseItem = courseItem

        with(holder.binding){
            courseItem?.let{
            rating.text = courseItem.rating.toString().substring(0, 3)
            date.text = DateConverter.convert(courseItem.date)
            Glide.with(cover).load(courseItem.img).into(cover)
            }
        }

        holder.binding.details.setOnClickListener {
            courseItem?.let{
                onDetailsClick(courseItem)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        return CourseViewHolder(
            CourseCardViewWideBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    class DiffUtilCallback: DiffUtil.ItemCallback<CourseItem>(){
        override fun areItemsTheSame(oldItem: CourseItem, newItem: CourseItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CourseItem, newItem: CourseItem): Boolean =
            oldItem == newItem

    }
}