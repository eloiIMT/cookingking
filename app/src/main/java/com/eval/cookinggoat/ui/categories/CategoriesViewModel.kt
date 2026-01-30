package com.eval.cookinggoat.ui.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eval.cookinggoat.data.repository.FoodRepositoryImpl
import com.eval.cookinggoat.domain.repository.FoodRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoriesViewModel(
    private val foodRepository: FoodRepository = FoodRepositoryImpl()
) : ViewModel() {

    private val _state: MutableStateFlow<CategoriesState> = MutableStateFlow(CategoriesState())

    val state: StateFlow<CategoriesState>
        get() = _state.asStateFlow()

    fun onEvent(event: CategoriesUIEvent) {
        when (event) {
            is CategoriesUIEvent.loadCategories -> getAllCategories()
        }
    }

    private fun getAllCategories() {
        viewModelScope.launch {
            _state.value.isLoading.let {
                foodRepository.getAllCategories().collect { result ->
                    result.fold(
                        onSuccess = { categories ->
                            _state.update { state ->
                                state.copy(isLoading = false, categories = categories)
                            }
                        },
                        onFailure = { error ->
                            _state.update { state ->
                                state.copy(isLoading = false, categories = emptyList())
                            }
                        }
                    )
                }
            }
        }
    }
}

sealed interface CategoriesUIEvent {
    data object loadCategories : CategoriesUIEvent
}