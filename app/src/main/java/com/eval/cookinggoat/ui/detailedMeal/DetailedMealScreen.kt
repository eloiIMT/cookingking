package com.eval.cookinggoat.ui.detailedMeal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
    categoryName: String,
    categoryDescription: String,
    state: MealsState,
    onEvent: (MealsUIEvent) -> Unit
) {
    LaunchedEffect(categoryName) {
        onEvent(MealsUIEvent.findReceipes(categoryName))
    }
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = categoryDescription,
            fontSize = 16.sp
        )
        Text(
            text = "Choose a meal :",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(12.dp),
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(12.dp),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(12.dp)
        ) {
            items(state.meals) { meal ->
                MealCard(
                    modifier = Modifier,
                    meal = meal,
                    onClick = { println("clicked")
                    }
                )
            }
        }
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }

}