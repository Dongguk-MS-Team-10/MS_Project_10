// MealViewModel.kt
package com.example.project_meal_test.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_meal_test.data.AppDatabase
import com.example.project_meal_test.data.Meal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.random.Random

class MealViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)
    private val mealDao = db.mealDao()

    // 최근 한 달간 식사 데이터 가져오기
    private val startDate = LocalDate.now().minusMonths(1).toString()

    val totalCalories: StateFlow<Int> = mealDao.getMealsFromDate(startDate)
        .map { meals ->
            meals.sumOf { it.calories }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val totalCost: StateFlow<Int> = mealDao.getTotalCostSince(startDate)
        .map { it ?: 0 } // null 값을 0으로 처리
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val breakfastCost: StateFlow<Int> = mealDao.getTotalCostByTypeSince("조식", startDate)
        .map { it ?: 0 }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val lunchCost: StateFlow<Int> = mealDao.getTotalCostByTypeSince("중식", startDate)
        .map { it ?: 0 }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val dinnerCost: StateFlow<Int> = mealDao.getTotalCostByTypeSince("석식", startDate)
        .map { it ?: 0 }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val snackCost: StateFlow<Int> = mealDao.getTotalCostByTypeSince("간식/음료", startDate)
        .map { it ?: 0 }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    // StateFlow로 UI가 변경 사항을 쉽게 관찰 가능하게 설정
    val meals: Flow<List<Meal>> = mealDao.getAllMeals()

    fun addMeal(meal: Meal) {
        viewModelScope.launch(Dispatchers.IO) {
            mealDao.insertMeal(meal)
        }
    }

    fun findMealById(mealId: Int): Flow<Meal?> = flow {
        mealDao.getMealById(mealId).collect { meal ->
            emit(meal)
        }
    }

    fun getRandomCalories(): Int {
        val calorieOptions = listOf(300, 400, 500)
        return calorieOptions[Random.nextInt(calorieOptions.size)]
    }

    // Other ViewModel logic
}