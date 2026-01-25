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


//class HomeViewModel(
//    private val movieRepository: MovieRepository = MovieRepositoryImpl()
//) : ViewModel() {
//
//    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
//    val state: StateFlow<HomeState>
//        get() = _state.asStateFlow()
//
//    init {
//        println("HomeViewModel initialized")
//    }
//
//    fun onEvent(event: HomeUIEvent) {
//        when (event) {
//            is HomeUIEvent.ChangeSearchText -> updateSearchText(event.text)
//            is HomeUIEvent.SubmitSearch -> searchMovie()
//            is HomeUIEvent.DeleteSearchText -> deleteSearchText()
//        }
//    }
//
//    private fun updateSearchText(searchText: String) {
//        _state.update {
//            it.copy(searchText = searchText)
//        }
//    }
//
//    private fun deleteSearchText() {
//        _state.update {
//            it.copy(searchText = "")
//        }
//    }
//
//    private fun searchMovie() {
//        viewModelScope.launch {
//            _state.value.searchText?.let {
//                _state.update { state ->
//                    state.copy(isLoading = true)
//                }
//                movieRepository.findMovie(name = it).collect { result ->
//                    result.fold(
//                        onSuccess = { movies ->
//                            _state.update { state ->
//                                state.copy(movies = movies, isLoading = false)
//                            }
//                        },
//                        onFailure = { error ->
//                            _state.update { state ->
//                                state.copy(movies = emptyList(), isLoading = false)
//                            }
//                        }
//                    )
//                }
//            }
//        }
//    }
//}
//
//sealed interface HomeUIEvent {
//    data class ChangeSearchText(val text: String) : HomeUIEvent
//    data object SubmitSearch : HomeUIEvent
//    data object DeleteSearchText : HomeUIEvent
//}
