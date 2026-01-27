package com.eval.cookinggoat.ui

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.eval.cookinggoat.ui.categories.CategoriesScreen
import com.eval.cookinggoat.ui.categories.CategoriesViewModel
import com.eval.cookinggoat.ui.detailedMeal.DetailedMealScreen
import com.eval.cookinggoat.ui.detailedMeal.DetailedMealViewModel
import com.eval.cookinggoat.ui.meals.MealsScreen
import com.eval.cookinggoat.ui.meals.MealsState
import com.eval.cookinggoat.ui.meals.MealsViewModel
import com.eval.cookinggoat.ui.theme.CookingKingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CookingKingTheme {
                val navController = rememberNavController()

                Scaffold { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = "Categories"
                    ) {
                        composable(route = "Categories") {
                            val categoriesViewModel: CategoriesViewModel = viewModel()
                            val state by categoriesViewModel.state.collectAsStateWithLifecycle()

                            CategoriesScreen(
                                modifier = Modifier.padding(paddingValues),
                                state = state,
                                onEvent = categoriesViewModel::onEvent,
                                onCategoryClick = { category ->
                                    val nameEncoded = Uri.encode(category.name)
                                    navController.navigate("Meals/$nameEncoded")
                                }
                            )
                        }
                        composable(
                            route = "Meals/{categoryName}",
                            arguments = listOf(
                                navArgument("categoryName") { type = NavType.StringType },
                            )
                        ) { backStackEntry ->
                            val categoryName = backStackEntry.arguments
                                ?.getString("categoryName")
                                ?.let { Uri.decode(it) }
                                .orEmpty()


                            val mealsViewModel: MealsViewModel = viewModel()
                            val state by mealsViewModel.state.collectAsStateWithLifecycle()

                            MealsScreen(
                                modifier = Modifier.padding(paddingValues),
                                categoryName = categoryName,
                                state = state,
                                onEvent = mealsViewModel::onEvent,
                                onBackClick = { navController.popBackStack() },
                                onMealClick = { meal ->
                                    val IdEncoded = Uri.encode(meal.id.toString())
                                    navController.navigate("Meals/$IdEncoded")
                                }
                            )
                        }
                        composable(
                            route = "Meals/{id}",
                            arguments = listOf(
                                navArgument("mealId") { type = NavType.StringType },
                            )
                        ) { backStackEntry ->
                            val mealId = backStackEntry.arguments
                                ?.getString("mealId")
                                ?.let { Uri.decode(it) }
                                .orEmpty()

                            Text(text = "Meal Details for ID: $mealId")

                            val detailedMealViewModel: DetailedMealViewModel = viewModel()
                            val state by detailedMealViewModel.state.collectAsStateWithLifecycle()

                            DetailedMealScreen(
                                modifier = Modifier.padding(paddingValues),
                                mealId = mealId,
                                state = state,
                                onEvent = detailedMealViewModel::onEvent,
                                onBackClick = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}