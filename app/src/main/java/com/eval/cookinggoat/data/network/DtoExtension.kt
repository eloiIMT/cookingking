package com.eval.cookinggoat.data.network

import com.eval.cookinggoat.data.network.dto.response.CategoryResponse
import com.eval.cookinggoat.data.network.dto.response.MealResponse
import com.eval.cookinggoat.domain.model.Category
import com.eval.cookinggoat.domain.model.Meal
import kotlin.text.orEmpty

fun CategoryResponse.toDomain(): List<Category> =
    categories.orEmpty().map { dto ->
        val name = dto.name
        Category(
            id = dto.id,
            name = name,
            imageUrl = dto.imageUrl,
            description = dto.description.orEmpty()
        )
    }

fun MealResponse.toDomain(): List<Meal> =
    categories.orEmpty().map { dto ->
        val name = dto.name
        Meal(
            id = dto.id,
            name = name,
            imageUrl = dto.imageUrl
        )
    }
