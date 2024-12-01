package com.example.project_meal_test.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
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
    // 식사 장소 선택 변수
    var selectedPlace by remember { mutableStateOf("") }
    var expandedPlace by remember { mutableStateOf(false) }

    // 종류 선택 변수
    var selectedType by remember { mutableStateOf("") }
    var expandedType by remember { mutableStateOf(false) }

    // 드롭다운 메뉴에 사용할 장소 리스트
    val places = listOf("상록원 2층", "상록원 3층", "기숙사 식당")
    val types = listOf("조식", "중식", "석식", "간식/음료")

    var foodName by remember { mutableStateOf(TextFieldValue("")) }
    var review by remember { mutableStateOf(TextFieldValue("")) }
    var date by remember { mutableStateOf(TextFieldValue("")) }
    var cost by remember { mutableStateOf(TextFieldValue("")) }
    var imageUri by remember { mutableStateOf(TextFieldValue("")) }

    Box(
        modifier = Modifier
            .fillMaxSize(), // 부모 크기만큼 Box 확장
        contentAlignment = Alignment.TopCenter // Box 내부에서 내용 상단 중앙 정렬
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // 식사 장소 제목
            Text(text = "식사 장소", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            // 드롭다운 메뉴로 식사 장소 선택
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(
                    onClick = { expandedPlace = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = if (selectedPlace.isEmpty()) "식사 장소 선택" else selectedPlace)
                }
                DropdownMenu(
                    expanded = expandedPlace,
                    onDismissRequest = { expandedPlace = false }
                ) {
                    places.forEach { place ->
                        DropdownMenuItem(
                            text = { Text(place) },
                            onClick = {
                                selectedPlace = place
                                expandedPlace = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 음식 이름 텍스트 필드
            TextField(
                value = foodName,
                onValueChange = { foodName = it },
                label = { Text("음식 이름") },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 소감 텍스트 필드
            TextField(
                value = review,
                onValueChange = { review = it },
                label = { Text("소감") },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 날짜 텍스트 필드
            TextField(
                value = date,
                onValueChange = { date = it },
                label = { Text("날짜 (yyyy-mm-dd)") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 종류 제목
            Text(text = "종류", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            // 드롭다운 메뉴로 종류 선택
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(
                    onClick = { expandedType = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = if (selectedType.isEmpty()) "종류 선택" else selectedType)
                }
                DropdownMenu(
                    expanded = expandedType,
                    onDismissRequest = { expandedType = false }
                ) {
                    types.forEach { type ->
                        DropdownMenuItem(
                            text = { Text(type) },
                            onClick = {
                                selectedType = type
                                expandedType = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 비용 텍스트 필드
            TextField(
                value = cost,
                onValueChange = { cost = it },
                label = { Text("비용") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 사진 URL 텍스트 필드
            TextField(
                value = imageUri,
                onValueChange = { imageUri = it },
                label = { Text("사진 URL") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 저장하기 버튼
            Button(
                onClick = {
                    try {
                        viewModel.addMeal(
                            Meal(
                                place = selectedPlace,
                                foodName = foodName.text,
                                cost = cost.text.toIntOrNull() ?: 0,
                                review = review.text,
                                date = date.text,
                                type = selectedType,
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
                modifier = Modifier
                    .width(280.dp)
                    .align(Alignment.CenterHorizontally)
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
                modifier = Modifier
                    .width(280.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text("목록으로 돌아가기")
            }
        }
    }
}
