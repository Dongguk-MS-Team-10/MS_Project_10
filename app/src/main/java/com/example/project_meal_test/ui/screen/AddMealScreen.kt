package com.example.project_meal_test.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
    var cost by remember { mutableStateOf(TextFieldValue("")) }

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
            value = cost,
            onValueChange = { cost = it },
            label = { Text("비용") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            viewModel.addMeal(
                Meal(place = place.text, foodName = foodName.text, cost = cost.text.toIntOrNull() ?: 0, review = review.text)
            )
        }) {
            Text("식사 추가")
        }
    }
}