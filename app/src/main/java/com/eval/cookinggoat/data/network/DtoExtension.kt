package com.eval.cookinggoat.data.network

import com.eval.cookinggoat.data.network.dto.response.CategoryResponse
import com.eval.cookinggoat.data.network.dto.response.MealDetailDTO
import com.eval.cookinggoat.data.network.dto.response.MealListResponse
import com.eval.cookinggoat.domain.model.Category
import com.eval.cookinggoat.domain.model.Meal
import com.eval.cookinggoat.domain.model.MealDetails
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

fun MealListResponse.toDomain(): List<Meal> =
    meals.orEmpty().map { dto ->
        val name = dto.name
        Meal(
            id = dto.id,
            name = name,
            imageUrl = dto.imageUrl
        )
    }

fun MealDetailDTO.toDomain(): MealDetails {
    val ingredients = mutableListOf<String>()
    val measures = mutableListOf<String>()

    for (i in 1..20) {
        val ingredientField = this::class.java.getDeclaredField("strIngredient$i")
        val measureField = this::class.java.getDeclaredField("strMeasure$i")

        ingredientField.isAccessible = true
        measureField.isAccessible = true

        val ingredient = ingredientField.get(this) as? String
        val measure = measureField.get(this) as? String

        if (!ingredient.isNullOrBlank()) {
            ingredients.add(ingredient)
            measures.add(measure.orEmpty())
        }
    }

    return MealDetails(
        id = idMeal.orEmpty(),
        name = strMeal.orEmpty(),
        category = strCategory.orEmpty(),
        area = strArea.orEmpty(),
        instructions = strInstructions.orEmpty(),
        thumbnail = strMealThumb.orEmpty(),
        tags = strTags?.split(",")?.map { it.trim() } ?: emptyList(),
        youtubeUrl = strYoutube,
        ingredients = ingredients,
        measures = measures,
        source = strSource
    )
}