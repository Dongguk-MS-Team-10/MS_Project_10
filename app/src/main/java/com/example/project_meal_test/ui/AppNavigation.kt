package com.example.project_meal_test.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.project_meal_test.ui.screen.AddMealScreen
import com.example.project_meal_test.ui.screen.AnalysisScreen
import com.example.project_meal_test.ui.screen.MealDetailScreen
import com.example.project_meal_test.ui.screen.MealListScreen
import com.example.project_meal_test.ui.screen.SplashScreen
import com.example.project_meal_test.viewmodel.MealViewModel

@Composable
fun AppNavigation(navController: NavHostController, viewModel: MealViewModel) {
    NavHost(
        navController = navController,
        startDestination = "splash_list" // 시작 화면
    ) {

        composable("splash_list") {
            SplashScreen(onTimeout = {
                navController.navigate("meal_list") {
                    popUpTo("splash") { inclusive = true }
                }
            })
        }
        // 식사 리스트 화면
        composable("meal_list") {
            MealListScreen(
                viewModel = viewModel,
                onMealClick = { mealId ->
                navController.navigate("meal_detail/$mealId")
            }, onAddMealClick = {
                navController.navigate("add_meal")
            },  onAnalysisClick = {
                navController.navigate("analysis")
                },

//                meals = listOf(
//                    Meal(1, "상록원 2층", "비빔밥", 6000, "맛있어요!","2024-11-27","석식","비빔밥사진"),
//                    Meal(2, "기숙사 식당", "된장찌개", 5000, "짭짤하다","2024-11-28","중식","된장찌개사진")
//                )

                meals = viewModel.meals
            )
        }

        // 식사 입력 화면
        composable("add_meal") {
            AddMealScreen(navController, viewModel)
        }

        // 식사 상세 화면
        composable("meal_detail/{mealId}") { backStackEntry ->
            val mealId = backStackEntry.arguments?.getString("mealId")?.toIntOrNull()
            mealId?.let {
                MealDetailScreen(mealId = it)
            }
        }

        // 식사 분석 화면
        composable("analysis") {
            AnalysisScreen()
        }
    }
}