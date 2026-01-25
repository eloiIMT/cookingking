package com.eval.cookinggoat.ui.meals

import com.eval.cookinggoat.domain.model.Meal

data class MealsState(
    val isLoading: Boolean = true,
    val meals: List<Meal> = emptyList()
)