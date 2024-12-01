package com.example.project_meal_test.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.project_meal_test.data.Meal
import com.example.project_meal_test.viewmodel.MealViewModel

@Composable
fun AddMealScreen(
    navController: NavController,
    viewModel: MealViewModel = viewModel()
) {
    var place by remember { mutableStateOf(TextFieldValue("")) }
    var foodName by remember { mutableStateOf(TextFieldValue("")) }
    var review by remember { mutableStateOf(TextFieldValue("")) }
    var date by remember { mutableStateOf(TextFieldValue("")) }
    var type by remember { mutableStateOf(TextFieldValue("")) }
    var cost by remember { mutableStateOf(TextFieldValue("")) }
    var imageUri by remember { mutableStateOf(TextFieldValue("")) }

    Box(
        modifier = Modifier
            .fillMaxSize(), // 부모 크기만큼 Box 확장
        contentAlignment = Alignment.TopCenter // Box 내부에서 내용 상단 중앙 정렬
    ) {
        Column(modifier = Modifier.padding(top = 100.dp)) {
            TextField(
                value = place,
                onValueChange = { place = it },
                label = { Text("식사 장소") },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )
            TextField(
                value = foodName,
                onValueChange = { foodName = it },
                label = { Text("음식 이름") },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )
            TextField(
                value = review,
                onValueChange = { review = it },
                label = { Text("소감") },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )
            TextField(
                value = date,
                onValueChange = { date = it },
                label = { Text("날짜 (yyyy-mm-dd)") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            )
            TextField(
                value = type,
                onValueChange = { type = it },
                label = { Text("종류 (조식, 중식, 석식, 간식/음료)") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            )
            TextField(
                value = cost,
                onValueChange = { cost = it },
                label = { Text("비용") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = imageUri,
                onValueChange = { imageUri = it },
                label = { Text("사진 URL") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 저장하기 버튼
            Button(
                onClick = {
                    try {
                        viewModel.addMeal(
                            Meal(
                                place = place.text,
                                foodName = foodName.text,
                                cost = cost.text.toIntOrNull() ?: 0,
                                review = review.text,
                                date = date.text,
                                type = type.text,
                                imageUri = imageUri.text
                            )
                        )
                        // 성공적으로 저장 시 로그 출력
                        Log.d("AddMealScreen", "Meal successfully saved: $foodName at $date")

                        // MealListScreen으로 이동
                        navController.navigate("meal_list") {
                            popUpTo("add_meal") { inclusive = true } // 스택에서 현재 화면 제거
                        }
                    } catch (e: Exception) {
                        Log.e("AddMealScreen", "Error saving meal: ${e.message}", e)
                    }
                },
                modifier = Modifier.width(280.dp)
            ) {
                Text("저장하기")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 목록으로 돌아가는 버튼
            Button(
                onClick = {
                    // MealListScreen으로 이동
                    navController.navigate("meal_list")
                },
                modifier = Modifier.width(280.dp)
            ) {
                Text("목록으로 돌아가기")
            }
        }
    }
}
