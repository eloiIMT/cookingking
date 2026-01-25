package com.eval.cookinggoat.ui.detailedMeal

import com.eval.cookinggoat.domain.model.Meal

data class DetailedMealState(
    val isLoading: Boolean = true,
    val meals: List<Meal> = emptyList()
)