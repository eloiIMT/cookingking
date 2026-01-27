package com.eval.cookinggoat.ui.detailedMeal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eval.cookinggoat.ui.component.MealCard
import com.eval.cookinggoat.ui.meals.MealsState

@Composable
fun DetailedMealScreen (
    modifier: Modifier,
    mealId: String,
    state: MealsState,
    onEvent: (MealsUIEvent) -> Unit,
    onBackClick: () -> Unit
) {

    Text(
        text = mealId
    )

}