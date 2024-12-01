package com.example.project_meal_test.ui.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.project_meal_test.R
import com.example.project_meal_test.data.Meal
import com.example.project_meal_test.viewmodel.MealViewModel
import java.util.Calendar

@Composable
fun AddMealScreen(
    navController: NavController,
    viewModel: MealViewModel = viewModel()
) {
    // 식사 장소 선택 변수
    var selectedPlace by remember { mutableStateOf("") }
    var expandedPlace by remember { mutableStateOf(false) }
    var customPlace by remember { mutableStateOf(TextFieldValue("")) } // 직접 입력할 장소 변수

    // 종류 선택 변수
    var selectedType by remember { mutableStateOf("") }
    var expandedType by remember { mutableStateOf(false) }

    // 드롭다운 메뉴에 사용할 장소 리스트
    val places = listOf("상록원 1층", "상록원 2층", "기숙사 식당", "기타")
    val types = listOf("조식", "중식", "석식", "간식/음료")

    var foodName by remember { mutableStateOf(TextFieldValue("")) }
    var review by remember { mutableStateOf(TextFieldValue("")) }
    var date by remember { mutableStateOf(TextFieldValue("")) }
    var cost by remember { mutableStateOf(TextFieldValue("")) }
    var imageUrl by remember { mutableStateOf(TextFieldValue("")) }

    // 미리 준비된 이미지 리스트 (리소스 ID)
    val imageResourcesForPlace = when (selectedPlace) {
        "상록원 1층" -> listOf(R.drawable.image1, R.drawable.image2, R.drawable.image3)
        "상록원 2층" -> listOf(R.drawable.image4, R.drawable.image5)
        else -> emptyList()
    }

    var selectedImage by remember { mutableStateOf<Int?>(null) } // 선택된 이미지의 리소스 ID
// DatePickerDialog 관련 변수
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = android.app.DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            date = TextFieldValue("$selectedYear-${selectedMonth + 1}-$selectedDay") // 선택된 날짜 저장
        },
        year,
        month,
        day
    )
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
            Spacer(modifier = Modifier.height(20.dp))
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

            // 사용자가 "기타"를 선택한 경우 직접 장소 입력 필드 표시
            if (selectedPlace == "기타") {
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = customPlace,
                    onValueChange = { customPlace = it },
                    label = { Text("직접 입력할 장소") },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    modifier = Modifier.fillMaxWidth()
                )
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


            // 날짜 텍스트 필드 (달력 다이얼로그를 통해 날짜 선택)
            OutlinedButton(
                onClick = { datePickerDialog.show() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = if (date.text.isEmpty()) "날짜 선택 (yyyy-mm-dd)" else date.text)
            }

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

            // "기타" 선택 시 직접 URL 입력 필드 표시
            if (selectedPlace == "기타" || selectedPlace == "기숙사 식당") {
                TextField(
                    value = imageUrl,
                    onValueChange = { imageUrl = it },
                    label = { Text("사진 URL 입력") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

            } else if (imageResourcesForPlace.isNotEmpty()) {
                // 미리 준비된 이미지들 중에서 선택
                Text(text = "사진 선택", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(imageResourcesForPlace) { imageRes ->
                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = null,
                            modifier = Modifier
                                .size(100.dp)
                                .clickable {
                                    selectedImage = imageRes
                                }
                                .border(
                                    width = 2.dp,
                                    color = if (selectedImage == imageRes) Color.Blue else Color.Transparent
                                )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            // 저장하기 버튼
            Button(
                onClick = {
                    try {
                        // 사용자가 직접 입력한 장소를 사용할 경우 customPlace 사용
                        val placeToSave = if (selectedPlace == "기타") customPlace.text else selectedPlace
                        val imageUriToSave = if (selectedPlace == "기타" || selectedPlace == "기숙사 식당") imageUrl.text else selectedImage?.toString()

                        if (!imageUriToSave.isNullOrEmpty()) {
                            viewModel.addMeal(
                                Meal(
                                    place = placeToSave,
                                    foodName = foodName.text,
                                    cost = cost.text.toIntOrNull() ?: 0,
                                    review = review.text,
                                    date = date.text,
                                    type = selectedType,
                                    imageUri = imageUriToSave,
                                    calories = viewModel.getRandomCalories() // 랜덤 칼로리 값
                                )
                            )
                            // 성공적으로 저장 시 로그 출력
                            Log.d("AddMealScreen", "Meal successfully saved: $foodName at $date")

                            // MealListScreen으로 이동
                            navController.navigate("meal_list") {
                                popUpTo("add_meal") { inclusive = true } // 스택에서 현재 화면 제거
                            }
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
