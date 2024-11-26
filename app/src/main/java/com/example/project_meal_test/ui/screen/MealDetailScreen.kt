package com.example.project_meal_test.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.project_meal_test.data.Meal

@Composable
fun MealDetailScreen(mealId: Int) {
    Text("식사 상세 화면 - ID: $mealId")
    /*
    val meal = findMealById(mealId)
    */

    val meal = Meal(3, "기숙사 식당", "된장찌개", 5000, "짭짤하다","2024-11-28","중식","된장찌개사진")


    Column(modifier = Modifier.fillMaxSize().padding(16.dp)){

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.fillMaxSize())
        {

                Text(text = meal.imageUri
                ,modifier = Modifier.align(Alignment.Center))


            Column(modifier = Modifier.align(Alignment.BottomStart)){
                Text("음식 : "+meal.foodName)
                Text("식당 : " +meal.place)
                Text("종류 : "+meal.type)
                Text("비용 : "+meal.cost.toString())
                Text("날짜 : " + meal.date)
                Text("소감 : "+meal.review)
                Text("칼로리량 : ")
            }
        }
    }
}
