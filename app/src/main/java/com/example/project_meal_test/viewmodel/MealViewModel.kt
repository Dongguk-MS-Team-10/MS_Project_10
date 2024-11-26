package com.example.project_meal_test.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_meal_test.data.Meal
import com.example.project_meal_test.data.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.log

class MealViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)
    private val mealDao = db.mealDao()

    fun addMeal(meal: Meal) {
        viewModelScope.launch(Dispatchers.IO) {
            mealDao.insertMeal(meal)
        }
    }

    // Other ViewModel logic
}