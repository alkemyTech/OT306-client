package com.melvin.ongandroid.model.news

data class News(
    val id: Int,
    val name: String,
    val content: String,
    val image: String,
    val user_id: Int,
    val category_id: Int,
    val created_at: String,
)
