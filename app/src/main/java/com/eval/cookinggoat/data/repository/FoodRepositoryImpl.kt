package com.eval.cookinggoat.data.repository

import com.eval.cookinggoat.data.network.ApiClient
import com.eval.cookinggoat.data.network.ApiService
import com.eval.cookinggoat.data.network.toDomain
import com.eval.cookinggoat.domain.model.Meal
import com.eval.cookinggoat.domain.repository.FoodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class FoodRepositoryImpl(
    private val apiService: ApiService = ApiClient.create()
) : FoodRepository {

    override suspend fun findMeals(categoryName: String) = flow {
        val response = apiService.searchMeals(
            categoryName = categoryName
        )
        emit(Result.success(response.toDomain()))
    }.catch { throwable ->
        emit(Result.failure(throwable))
    }

    override suspend fun getAllCategories() = flow {
        val response = apiService.getAllCategories()
        emit(Result.success(response.toDomain()))
    }.catch { throwable ->
        emit(Result.failure(throwable))
    }

    override suspend fun findMealDetails(mealId: Int) = flow {
        val response = apiService.getMealDetails(
            mealId = mealId
        ).mealDetail?.get(0)
        if (response == null) {
            emit(Result.failure(Exception("Meal not found")))
            return@flow
        }
        emit(Result.success(response.toDomain()))
    }.catch { throwable ->
        emit(Result.failure(throwable))
    }
}