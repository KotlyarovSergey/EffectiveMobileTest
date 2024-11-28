package com.ksv.effectivemobiletest.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ksv.effectivemobiletest.databinding.CoureseCardViewBinding
import com.ksv.effectivemobiletest.entity.CourseItem
import com.ksv.effectivemobiletest.utils.DateConverter

class CourseListAdapter(
    private val onDetailsClick: (position: Int) -> Unit
): ListAdapter<CourseItem, CourseListAdapter.CourseViewHolder>(DiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val holder = CourseViewHolder(
                CoureseCardViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

        holder.binding.details.setOnClickListener {
            onDetailsClick(holder.adapterPosition)
        }

        return holder
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val courseItem = getItem(position)
        with(holder.binding){
            courseItem?.let{
                title.text = courseItem.name
                summary.text = courseItem.description
                price.text = courseItem.cost.toString()
                rating.text = courseItem.rating.toString().substring(0, 3)
                date.text = DateConverter.convert(courseItem.date)
                Glide.with(cover).load(courseItem.img).into(cover)
//                cover.setImageURI(courseItem.img.toUri())
            }
        }
    }

    class CourseViewHolder(val binding: CoureseCardViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    class DiffUtilCallback: DiffUtil.ItemCallback<CourseItem>(){
        override fun areItemsTheSame(oldItem: CourseItem, newItem: CourseItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CourseItem, newItem: CourseItem): Boolean =
            oldItem == newItem

    }




}