package com.ksv.effectivemobiletest.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.ksv.effectivemobiletest.databinding.CoureseCardViewBinding
import com.ksv.effectivemobiletest.entity.CourseItem
import com.ksv.effectivemobiletest.utils.DateConverter

class CourseCardAdapter(
    private val onDetailsClick: (position: Int) -> Unit
) : RecyclerView.Adapter<CourseCardAdapter.CourseViewHolder>() {
    private var courses: List<CourseItem> = mutableListOf()



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
        val courseItem = courses.getOrNull(position)
        with(holder.binding){
            courseItem?.let{
                title.text = courseItem.name
                summary.text = courseItem.description
                price.text = courseItem.cost.toString()
                rating.text = courseItem.rating.toString()
                date.text = DateConverter.convert(courseItem.date)
                cover.setImageURI(courseItem.link.toUri())
            }
        }
    }

    override fun getItemCount(): Int {
        return courses.size
    }

    fun setDate(courses: List<CourseItem>){
        this.courses = courses
        notifyItemRangeChanged(0, courses.size)
    }

    class CourseViewHolder(val binding: CoureseCardViewBinding) :
        RecyclerView.ViewHolder(binding.root)
}