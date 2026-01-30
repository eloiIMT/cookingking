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

    private val _state: MutableStateFlow<DetailedMealState> = MutableStateFlow(DetailedMealState())

    val state: StateFlow<DetailedMealState>
        get() = _state.asStateFlow()

    fun onEvent(event: DetailsUIEvent) {
        when (event) {
            is DetailsUIEvent.getReceipe -> getReceipe(event.mealId)
            is DetailsUIEvent.NavigateToVideo -> {

            }
        }
    }

    private fun getReceipe(mealId: Int) {
        viewModelScope.launch {
            _state.value.isLoading.let {
                foodRepository.findMealDetails(mealId).collect { result ->
                    result.fold(
                        onSuccess = { details ->
                            _state.update { state ->
                                state.copy(isLoading = false, mealDetails = details)
                            }
                        },
                        onFailure = { error ->
                            _state.update { state ->
                                state.copy(isLoading = false)
                            }
                        }
                    )
                }
            }
        }
    }
}

sealed interface DetailsUIEvent {
    data class getReceipe(val mealId: Int) : DetailsUIEvent
    data class NavigateToVideo(val videoId: String) : DetailsUIEvent
}
