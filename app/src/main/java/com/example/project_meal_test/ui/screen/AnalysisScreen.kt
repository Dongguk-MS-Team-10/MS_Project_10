package com.example.project_meal_test.ui.screen


import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AnalysisScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        // 큰 글씨
        Text(
            text = "최근 1달 간의 식단 분석 ",
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(25.dp))

        // 칼로리 총량
        Text(
            text = "섭취한 칼로리 총량 :",
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
                text = "조식 :",
                fontSize = 20.sp
            )
            Text(
                text = "중식 :",
                fontSize = 20.sp
            )
            Text(
                text = "석식 :",
                fontSize = 20.sp
            )
            Text(
                text = "간식/음료 :",
                fontSize = 20.sp
            )
        }
    }
}