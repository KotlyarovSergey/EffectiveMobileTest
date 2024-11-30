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
    val link: String,
    val learners: Int
) {
    override fun toString(): String {
        return "id: $id, $name ${description.substring(0..10)}... $rating $date $cost $price $learners"
    }
}
