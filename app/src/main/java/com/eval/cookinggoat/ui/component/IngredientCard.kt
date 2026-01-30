package com.eval.cookinggoat.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun IngredientCard(
    ingredient: String,
    measure: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(horizontal = 4.dp, vertical = 3.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = ingredient,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal
            )
            if (measure.isNotBlank()) {
                Text(
                    text = ": $measure",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun IngredientsGrid(
    ingredients: List<String>,
    measures: List<String>,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier.wrapContentWidth().border(
            width = 1.dp,
            color = Color.Black,
            shape = RoundedCornerShape(4.dp)
        ).padding(horizontal = 6.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        ingredients.forEachIndexed { index, ingredient ->
            if (ingredient.isNotBlank() && index < measures.size) {
                IngredientCard(
                    ingredient = ingredient,
                    measure = measures[index]
                )
            }
        }
    }
}
