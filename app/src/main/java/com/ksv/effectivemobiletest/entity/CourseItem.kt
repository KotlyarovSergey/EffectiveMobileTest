package com.ksv.effectivemobiletest.entity


data class CourseItem(
    val id: Int,
    val name: String,
    val rating: Double = 0.0,
    val date: String,
    val description: String,
    val isFavourite: Boolean = false,
    val cost: Int,
    val price: String?,
    val img: String,
    val link: String
) {
    override fun toString(): String {
//        return super.toString()
        return "$name ${description.substring(0..10)}... $rating $date $cost"
    }
}
