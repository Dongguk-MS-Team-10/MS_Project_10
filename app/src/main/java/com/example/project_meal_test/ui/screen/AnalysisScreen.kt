package com.example.project_meal_test.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
            .padding(top = 50.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        // 큰 글씨
        Text(
            text = "최근 1달 간의 식단 분석",
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(25.dp))

        // 칼로리 총량 (비용 대신 식사 비용으로 표시)
        Text(
            text = "섭취한 비용 총량: $totalCost 원",
            fontSize = 25.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(start = 20.dp)
        )

        //칼로리 총량
        Text(
            text = "칼로리 총량 : $totalCalories kcal",
            fontSize = 25.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(start = 20.dp)
        )

        // 종류별 식사 비용
        Text(
            text = "종류별 식사 비용",
            fontSize = 25.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(start = 20.dp)
        )

        Column(
            modifier = Modifier.padding(start = 30.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "조식: $breakfastCost 원",
                fontSize = 20.sp
            )
            Text(
                text = "중식: $lunchCost 원",
                fontSize = 20.sp
            )
            Text(
                text = "석식: $dinnerCost 원",
                fontSize = 20.sp
            )
            Text(
                text = "간식/음료: $snackCost 원",
                fontSize = 20.sp
            )
        }
    }
}
