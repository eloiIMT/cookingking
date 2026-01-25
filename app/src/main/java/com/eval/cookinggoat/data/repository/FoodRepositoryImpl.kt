package com.eval.cookinggoat.data.repository

import com.eval.cookinggoat.data.network.ApiClient
import com.eval.cookinggoat.data.network.ApiService
import com.eval.cookinggoat.data.network.toDomain
import com.eval.cookinggoat.domain.repository.FoodRepository
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
}

//class MovieRepositoryImpl(
//    private val apiService: ApiService = ApiClient.create()
//) : MovieRepository {
//
//    override suspend fun findMovie(name: String) = flow {
//        val response = apiService.searchMovie(
//            search = name
//        )
//        emit(Result.success(response.toDomain()))
//    }.catch { throwable ->
//        emit(Result.failure(throwable))
//    }
//
//    override suspend fun findMovieDetail(id: String) = flow {
//        val response = apiService.searchMovieDetail(
//            search = id
//        )
//        emit(Result.success(response.toDomain()))
//    }.catch { throwable ->
//        emit(Result.failure(throwable))
//    }
//}