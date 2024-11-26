package com.example.project_meal_test.data

import androidx.room.*

@Dao
interface MealDao {
    @Query("SELECT * FROM Meal")
    fun getAllMeals(): List<Meal>

    @Insert
    fun insertMeal(meal: Meal)

    @Delete
    fun deleteMeal(meal: Meal)
}
