// MealDetailScreen.kt
package com.example.project_meal_test.ui.screen

import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project_meal_test.viewmodel.MealViewModel

@Composable
fun MealDetailScreen(mealId: Int, viewModel: MealViewModel = viewModel()) {
    val mealFlow = viewModel.findMealById(mealId)
    val meal by mealFlow.collectAsState(initial = null)

    meal?.let { meal ->
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Spacer(modifier = Modifier.height(16.dp))

            Box(modifier = Modifier.fillMaxSize()) {
                Text(text = meal.imageUri, modifier = Modifier.align(Alignment.Center))

                Column(modifier = Modifier.align(Alignment.BottomStart)) {
                    Text("음식 : " + meal.foodName)
                    Text("식당 : " + meal.place)
                    Text("종류 : " + meal.type)
                    Text("비용 : " + meal.cost.toString())
                    Text("날짜 : " + meal.date)
                    Text("소감 : " + meal.review)
                    Text("칼로리량 : ")
                }
            }
        }
    } ?: run {
        Text("식사 상세 화면 - ID: $mealId")
    }
}