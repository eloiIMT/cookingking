package com.eval.cookinggoat.domain.model

data class MealDetails(
    val id: String,
    val name: String,
    val category: String,
    val area: String,
    val instructions: String,
    val thumbnail: String,
    val tags: List<String>,
    val youtubeUrl: String?,
    val ingredients: List<String>,
    val measures: List<String>,
    val source: String?
)