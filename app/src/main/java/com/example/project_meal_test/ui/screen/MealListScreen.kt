package com.example.project_meal_test.ui.screen

// 기본 UI 요소
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// 데이터 모델
import com.example.project_meal_test.data.Meal
import com.example.project_meal_test.viewmodel.MealViewModel


@Composable
fun MealListScreen(
    viewModel: MealViewModel,
    onMealClick: (Int) -> Unit,     // 특정 식사를 클릭했을 때의 이벤트
    onAddMealClick: () -> Unit,      // 식사 추가 버튼 클릭 이벤트
    onAnalysisClick: () -> Unit,     // 식단 분석 버튼 클릭 이벤트
    meals: List<Meal>              // 실제 식사 데이터를 리스트로 전달

) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // 식단 분석 버튼
        Button(
            onClick = onAnalysisClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "식단 분석")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 식사 추가 버튼
        Button(
            onClick = onAddMealClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "식사 추가")
        }

        Spacer(modifier = Modifier.height(16.dp)) // 간격 추가

        // 식사 리스트
        if (meals.isEmpty()) {
            // 데이터가 없을 때 표시
            Text(
                text = "등록된 식사가 없습니다.",
                style = TextStyle(
                    fontSize = 16.sp, // 폰트 크기 설정
                    lineHeight = 24.sp, // 줄 높이 설정
                    lineHeightStyle = LineHeightStyle(
                        alignment = LineHeightStyle.Alignment.Center,
                        trim = LineHeightStyle.Trim.None
                    )
                ),
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(meals) { meal ->
                    MealItem(meal = meal, onClick = { onMealClick(meal.id) })
                }
            }
        }
    }
}


@Composable
fun MealItem(
    meal: Meal,                     // 단일 식사 데이터
    onClick: () -> Unit             // 클릭 이벤트
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = meal.foodName, style = MaterialTheme.typography.bodyLarge)
            Text(text = meal.place, style = MaterialTheme.typography.bodySmall)
            Text(text = meal.date, style = MaterialTheme.typography.bodySmall)
        }
        Text(text = "${meal.cost}원", style = MaterialTheme.typography.bodySmall)
    }
}

