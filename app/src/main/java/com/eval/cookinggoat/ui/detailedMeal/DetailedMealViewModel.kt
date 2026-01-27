package com.eval.cookinggoat.ui.detailedMeal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eval.cookinggoat.data.repository.FoodRepositoryImpl
import com.eval.cookinggoat.domain.repository.FoodRepository
import com.eval.cookinggoat.ui.meals.MealsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailedMealViewModel(
    private val foodRepository: FoodRepository = FoodRepositoryImpl()
) : ViewModel() {

    private val _state: MutableStateFlow<MealsState> = MutableStateFlow(MealsState())

    val state: StateFlow<MealsState>
        get() = _state.asStateFlow()

    fun onEvent(event: MealsUIEvent) {
        when (event) {
            is MealsUIEvent.findReceipes -> findReceipes(event.categoryName)
        }
    }

    private fun findReceipes(categoryName: String) {
        viewModelScope.launch {
            _state.value.isLoading.let {
                foodRepository.findMeals(categoryName).collect { result ->
                    result.fold(
                        onSuccess = { meals ->
                            _state.update { state ->
                                state.copy(isLoading = false, meals = meals)
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
}

sealed interface MealsUIEvent {
    data class findReceipes(val categoryName: String) : MealsUIEvent
}
