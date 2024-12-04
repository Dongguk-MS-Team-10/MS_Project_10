package com.example.project_meal_test.ui.screen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.project_meal_test.R
import com.example.project_meal_test.data.Meal
import com.example.project_meal_test.viewmodel.MealViewModel
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun MealListScreen(
    viewModel: MealViewModel,
    onMealClick: (Int) -> Unit,
    onAddMealClick: () -> Unit,
    onAnalysisClick: () -> Unit,
    meals: Flow<List<Meal>>
) {
    val mealList by meals.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background) // 테마 배경색 적용
            .padding(16.dp)
    ) {
        // 상단 버튼들
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = onAnalysisClick,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "식단 분석")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = onAddMealClick,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "식사 추가")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 식사 리스트
        if (mealList.isEmpty()) {
            // 데이터가 없을 때 애니메이션 효과와 함께 표시
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
            ) {
                AnimatedVisibility(
                    visible = true,
                    modifier = Modifier.shadow(elevation = 6.dp, shape = RoundedCornerShape(12.dp)),
                    enter = fadeIn(),
                    exit = fadeOut(),
                    label = "No Data",

                ) {
                    Text(
                        text = "등록된 식사가 없습니다.",
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(mealList) { meal ->
                    MealItem(meal = meal, onClick = { onMealClick(meal.id) })
                }
            }
        }
    }
}

@Composable
fun MealItem(
    meal: Meal,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .shadow(elevation = 6.dp, shape = RoundedCornerShape(12.dp)), // 그림자 효과 추가
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 이미지 섹션
            MealImage(imageUri = meal.imageUri)

            Spacer(modifier = Modifier.width(12.dp))

            // 텍스트 정보
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = meal.foodName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Place,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = meal.place,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                val formattedDate = remember(meal.date) {
                    try {
                        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-d") // 예시 입력 형식
                        val outputFormatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일")

                        val date = LocalDate.parse(meal.date, inputFormatter)
                        date.format(outputFormatter)
                    }
                    catch (e: Exception) {
                        try {
                            val inputFormatter = DateTimeFormatter.ofPattern("yyyy-M-d") // 예시 입력 형식
                            val outputFormatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일")

                            val date = LocalDate.parse(meal.date, inputFormatter)
                            date.format(outputFormatter)
                        }
                        catch (e: Exception) {
                            meal.date
                        }
                    }

                }

                Log.d("MealItem", "formattedDate: $formattedDate, date: ${meal.date}")
                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // 비용 정보
            Text(
                text = "${meal.cost}원",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun MealImage(imageUri: String) {
    val imageModifier = Modifier
        .size(80.dp)
        .clip(RoundedCornerShape(8.dp))
        .border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
            shape = RoundedCornerShape(8.dp)
        )

    if (imageUri.isNotBlank()) {
        val isResourceId = imageUri.toIntOrNull() != null
        if (isResourceId) {
            val resourceId = imageUri.toInt()
            Image(
                painter = painterResource(id = resourceId),
                contentDescription = null,
                modifier = imageModifier,
                contentScale = ContentScale.Crop
            )
        } else if (imageUri.isNotBlank()) {

            SubcomposeAsyncImage(
                model = imageUri,
                contentDescription = null,
                modifier = imageModifier,
                loading = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary,
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                error = {
                    Image(
                        painter = painterResource(id = R.drawable.default_image),
                        contentDescription = null,
                        modifier = imageModifier,
                        contentScale = ContentScale.Crop
                    )
                },
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.default_image),
                contentDescription = null,
                modifier = imageModifier,
                contentScale = ContentScale.Crop
            )
        }
    }else {
        Icon(
            painter = painterResource(id = R.drawable.default_image),
            contentDescription = null,
            modifier = imageModifier,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
