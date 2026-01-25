package com.eval.cookinggoat.data.network
import com.eval.cookinggoat.data.network.dto.response.CategoryResponse
import com.eval.cookinggoat.data.network.dto.response.MealResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("categories.php")
    suspend fun getAllCategories(): CategoryResponse

    @GET("filter.php")
    suspend fun searchMeals(@Query("c") categoryName: String): MealResponse

//    @GET("search")
//    suspend fun searchReceipe(@Query("tt") search: String): MovieResponse
}