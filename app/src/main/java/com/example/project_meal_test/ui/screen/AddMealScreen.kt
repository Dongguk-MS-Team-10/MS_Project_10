package com.example.project_meal_test.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project_meal_test.data.Meal
import com.example.project_meal_test.viewmodel.MealViewModel

@Composable
fun AddMealScreen(viewModel: MealViewModel = viewModel()) {
    var place by remember { mutableStateOf(TextFieldValue("")) }
    var foodName by remember { mutableStateOf(TextFieldValue("")) }
    var review by remember { mutableStateOf(TextFieldValue("")) }
    var date by remember { mutableStateOf(TextFieldValue(""))}
    var type by remember { mutableStateOf(TextFieldValue(""))}
    var cost by remember { mutableStateOf(TextFieldValue("")) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    /* 갤러리에서 이미지 선택을 위한 Launcher
    val context = LocalContext.current
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri // 선택된 이미지 URI 저장
    }
    */


    Column(modifier = Modifier.padding(16.dp)) {
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
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            )
        )
        TextField(
            value = type,
            onValueChange = { type = it },
            label = { Text("종류 (조식, 중식, 석식, 간식/음료)") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
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


         Button(onClick = {}){
        // onClick = { imagePickerLauncher.launch("image/*") }) {
              Text("사진 추가")}



        Spacer(modifier = Modifier.height(16.dp))


        Button(onClick = {
            viewModel.addMeal(
                Meal(place = place.text, foodName = foodName.text
                    , cost = cost.text.toIntOrNull() ?: 0, review = review.text
                    , date=date.text, type = type.text, imageUri = imageUri.toString()
                )
            )
        }) {
            Text("식사 추가")
        }


    }




    }
