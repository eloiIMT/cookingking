package com.eval.cookinggoat.ui.meals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eval.cookinggoat.data.repository.FoodRepositoryImpl
import com.eval.cookinggoat.domain.model.Meal
import com.eval.cookinggoat.domain.repository.FoodRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MealsViewModel(
    private val foodRepository: FoodRepository = FoodRepositoryImpl()
) : ViewModel() {

    private val _state: MutableStateFlow<MealsState> = MutableStateFlow(MealsState())

    val state: StateFlow<MealsState>
        get() = _state.asStateFlow()

    fun onEvent(event: MealsUIEvent) {
        when (event) {
            is MealsUIEvent.findReceipes -> findReceipes(event.categoryName)
            is MealsUIEvent.searchQueryChange -> searchQueryChange(event.query)
        }
    }

    private fun findReceipes(categoryName: String) {
        viewModelScope.launch {
            _state.value.isLoading.let {
                foodRepository.findMeals(categoryName).collect { result ->
                    result.fold(
                        onSuccess = { meals ->
                            _state.update { state ->
                                state.copy(isLoading = false, meals = meals, filteredMeals = meals)
                            }
                        },
                        onFailure = { error ->
                            _state.update { state ->
                                state.copy(isLoading = false, meals = emptyList())
                            }
                        }
                    )
                }
            }
        }
    }

    private fun searchQueryChange(query: String) {
        _state.update { state ->
            state.copy(
                searchQuery = query,
                filteredMeals = if (query.isEmpty()) {
                    state.meals
                } else {
                    state.meals.filter { meal ->
                        meal.name.contains(query, ignoreCase = true)
                    }
                }
            )
        }
    }
}

sealed interface MealsUIEvent {
    data class findReceipes(val categoryName: String) : MealsUIEvent

    data class searchQueryChange(val query: String) : MealsUIEvent
}
