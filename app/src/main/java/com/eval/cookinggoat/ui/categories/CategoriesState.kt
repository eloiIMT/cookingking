package com.eval.cookinggoat.ui.categories

import com.eval.cookinggoat.domain.model.Category

data class CategoriesState(
    val isLoading: Boolean = true,
    val categories: List<Category> = emptyList()
)