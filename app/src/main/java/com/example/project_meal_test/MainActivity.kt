package com.example.project_meal_test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.project_meal_test.ui.AppNavigation
import com.example.project_meal_test.ui.theme.LightGreen900
import com.example.project_meal_test.ui.theme.Teal900
import com.example.project_meal_test.viewmodel.MealViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewModel 생성
        val viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(MealViewModel::class.java)
        val myColorScheme = lightColorScheme(
            primary = Teal900,
            secondary = LightGreen900,
        )

        setContent {
            MaterialTheme(colorScheme = myColorScheme) {
                val navController = rememberNavController()
                AppNavigation(navController = navController, viewModel) // 네비게이션 구성
            }
        }
    }
}