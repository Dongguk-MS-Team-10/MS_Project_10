package com.example.project_meal_test.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project_meal_test.viewmodel.MealViewModel

@Composable
fun AnalysisScreen(viewModel: MealViewModel = viewModel()) {
    // ViewModel에서 데이터를 가져옵니다.
    val totalCost by viewModel.totalCost.collectAsState()
    val breakfastCost by viewModel.breakfastCost.collectAsState()
    val lunchCost by viewModel.lunchCost.collectAsState()
    val dinnerCost by viewModel.dinnerCost.collectAsState()
    val snackCost by viewModel.snackCost.collectAsState()
    val totalCalories by viewModel.totalCalories.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        // 큰 글씨
        Text(
            text = "최근 1달 간의 식단 분석",
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(60.dp))

        // 총 비용 및 총 칼로리 섹션
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            shape = RoundedCornerShape(12.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "지출한 총비용: $totalCost 원",
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = "칼로리 총량 : $totalCalories kcal",
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(100.dp))

        // 종류별 식사 비용 섹션
        Text(
            text = "종류별 식사 비용",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(start = 8.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            shape = RoundedCornerShape(12.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "조식: $breakfastCost 원",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF6A1B9A)
                )
                Text(
                    text = "중식: $lunchCost 원",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF2E7D32)
                )
                Text(
                    text = "석식: $dinnerCost 원",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFFD84315)
                )
                Text(
                    text = "간식/음료: $snackCost 원",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF0288D1)
                )
            }
        }
    }
}
