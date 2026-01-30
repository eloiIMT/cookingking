package com.eval.cookinggoat.domain.repository

import com.eval.cookinggoat.domain.model.Category
import com.eval.cookinggoat.domain.model.Meal
import com.eval.cookinggoat.domain.model.MealDetails
import kotlinx.coroutines.flow.Flow

interface FoodRepository {

    suspend fun findMeals(categoryName: String): Flow<Result<List<Meal>>>

    suspend fun getAllCategories(): Flow<Result<List<Category>>>

    suspend fun findMealDetails(mealId: Int): Flow<Result<MealDetails>>
}