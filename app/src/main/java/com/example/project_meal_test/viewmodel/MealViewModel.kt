// MealViewModel.kt
package com.example.project_meal_test.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_meal_test.data.Meal
import com.example.project_meal_test.data.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MealViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)
    private val mealDao = db.mealDao()

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

    // Other ViewModel logic
}