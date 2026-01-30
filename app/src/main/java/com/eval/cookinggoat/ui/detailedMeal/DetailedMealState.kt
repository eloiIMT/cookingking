package com.eval.cookinggoat.ui.detailedMeal

import com.eval.cookinggoat.domain.model.MealDetails

data class DetailedMealState(
    val isLoading: Boolean = true,
    val mealDetails: MealDetails = MealDetails(
        id = "",
        name = "",
        category = "",
        area = "",
        instructions = "",
        thumbnail = "",
        tags = emptyList(),
        youtubeUrl = null,
        ingredients = emptyList(),
        measures = emptyList(),
        source = null
    ),
)