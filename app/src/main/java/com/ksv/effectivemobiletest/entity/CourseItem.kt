package com.ksv.effectivemobiletest.entity


data class CourseItem(
    val id: Int,
    val name: String,
    val rating: Float = 0.0f,
    val date: String,
    val description: String,
    val isFavourite: Boolean = false,
    val cost: Int,
    val img: String,
    val link: String
) {
    override fun toString(): String {
//        return super.toString()
        return "$name ${description.substring(0..10)}... $rating $date $cost"
    }
}