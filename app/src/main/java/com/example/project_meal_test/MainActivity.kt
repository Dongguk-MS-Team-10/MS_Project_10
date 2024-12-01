package com.example.project_meal_test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.project_meal_test.ui.AppNavigation
import com.example.project_meal_test.ui.theme.Project_Meal_TestTheme
import com.example.project_meal_test.viewmodel.MealViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 앱 시작 시 데이터베이스 파일 삭제
//        deleteDatabase("meal_database")

        // ViewModel 생성
        val viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(MealViewModel::class.java)

        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                AppNavigation(navController = navController, viewModel) // 네비게이션 구성
            }
        }
    }
}