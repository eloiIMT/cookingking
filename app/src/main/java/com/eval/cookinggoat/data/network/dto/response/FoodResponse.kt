package com.eval.cookinggoat.data.network.dto.response
import com.google.gson.annotations.SerializedName


data class CategoryResponse(
    @SerializedName("categories")
    val categories: List<CategoryDto>?
)

data class MealResponse(
    @SerializedName("meals")
    val categories: List<MealDto>?
)

data class CategoryDto(
    @SerializedName("idCategory")
    val id: Int,

    @SerializedName("strCategory")
    val name: String,

    @SerializedName("strCategoryThumb")
    val imageUrl: String,

    @SerializedName("strCategoryDescription")
    val description: String
)

data class MealDto(
    @SerializedName("idMeal")
    val id: Int,

    @SerializedName("strMeal")
    val name: String,

    @SerializedName("strMealThumb")
    val imageUrl: String,
)