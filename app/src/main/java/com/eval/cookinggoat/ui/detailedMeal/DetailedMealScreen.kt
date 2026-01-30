package com.eval.cookinggoat.ui.detailedMeal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.eval.cookinggoat.R
import com.eval.cookinggoat.ui.component.IngredientsGrid
import com.eval.cookinggoat.ui.component.YouTubeCard

@Composable
fun DetailedMealScreen(
    modifier: Modifier,
    mealId: Int,
    state: DetailedMealState,
    onEvent: (DetailsUIEvent) -> Unit,
    onBackClick: () -> Unit
) {
    LaunchedEffect(mealId) {
        onEvent(DetailsUIEvent.getReceipe(mealId))
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                AsyncImage(
                    model = state.mealDetails?.thumbnail ?: "",
                    contentDescription = state.mealDetails?.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                        )
                        .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color(0xAA000000)),
                                startY = 40f
                            )
                        )
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(20.dp),
                    text = state.mealDetails.name,
                    fontSize =24.sp,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            state.mealDetails.let { meal ->

                FlowRow(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    AssistChip(
                        onClick = {},
                        label = { Text(meal.category) },
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    AssistChip(
                        onClick = {},
                        label = { Text(meal.area) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.LocationOn,
                                contentDescription = null
                            )
                        }
                    )
                    meal.tags.forEach { tag ->
                        Spacer(modifier = Modifier.width(8.dp))
                        AssistChip(
                            onClick = {},
                            label = { Text(tag) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.ingredients_title),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                IngredientsGrid(
                    ingredients = meal.ingredients,
                    measures = meal.measures,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                val youtubeId = extractYouTubeId(meal.youtubeUrl)

                if (youtubeId.isNotBlank()) {
                    Text(
                        text = stringResource(R.string.receipe_video_name),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    println("Youtube ID: $youtubeId")
                    YouTubeCard(
                        videoId = youtubeId,
                        videoName = stringResource(R.string.receipe_video_name),
                        onVideoClick = { selectedId ->
                            onEvent(DetailsUIEvent.NavigateToVideo(selectedId))
                        }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = stringResource(R.string.instructions_title),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                val steps = splitInstructions(meal.instructions)

                steps.forEachIndexed { index, step ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {

                            if (steps.size > 1){
                                Text(
                                    text = stringResource(R.string.steps_title, index + 1),
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }


                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = step
                                    .replace(
                                        Regex("""^\s*(step\s*\d+|\d+\.?)""", RegexOption.IGNORE_CASE),
                                        ""
                                    )
                                    .trim(),
                                fontSize = 14.sp,
                                lineHeight = 20.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }

        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .padding(16.dp)
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(30.dp)
                )
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(30.dp)
                )
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.back_description)
            )
        }
    }
}

fun splitInstructions(instructions: String): List<String> {
    val normalized = instructions
        .replace("\r\n", "\n")
        .replace("\r", "\n")
        .trim()

    val stepRegex = Regex(
        """(?m)(^\s*(step\s*\d+|\d+\.?|\d+\s*$)|^\s*[A-Z][^.\n]{3,60}:)""".trimIndent(),
        RegexOption.IGNORE_CASE
    )

    val matches = stepRegex.findAll(normalized).toList()

    if (matches.isEmpty()) {
        return normalized
            .split("\n\n")
            .map { it.trim() }
            .filter { it.length > 20 }
    }

    return matches.mapIndexed { index, match ->
        val start = match.range.first
        val end = if (index + 1 < matches.size)
            matches[index + 1].range.first
        else
            normalized.length

        normalized.substring(start, end).trim()
    }
}

fun extractYouTubeId(url: String?): String {
    if (url.isNullOrBlank()) return ""

    val normalizedUrl = url.replace("\\/", "/").trim()

    return when {
        //youtube.com/watch?v=VIDEO_ID
        normalizedUrl.contains("watch?v=") ->
            normalizedUrl.substringAfter("v=")
                .substringBefore("&")
                .substringBefore("?")

        //youtube.com/shorts/VIDEO_ID
        normalizedUrl.contains("/shorts/") ->
            normalizedUrl.substringAfter("/shorts/")
                .substringBefore("?")
                .substringBefore("/")

        //youtu.be/VIDEO_ID
        normalizedUrl.contains("youtu.be/") ->
            normalizedUrl.substringAfter("youtu.be/")
                .substringBefore("?")
                .substringBefore("/")

        //youtube.com/embed/VIDEO_ID
        normalizedUrl.contains("/embed/") ->
            normalizedUrl.substringAfter("/embed/")
                .substringBefore("?")
                .substringBefore("/")

        else -> ""
    }.trim()
}