package com.example.project_meal_test.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface MealDao {
    @Query("SELECT * FROM Meal ORDER BY date DESC")
    fun getAllMeals(): Flow<List<Meal>>


    @Query("SELECT * FROM Meal WHERE date >= :startDate")
    fun getMealsFromDate(startDate: LocalDate): Flow<List<Meal>>

    @Query("SELECT * FROM Meal WHERE id = :id")
    fun getMealById(id: Int): Flow<Meal>

    @Insert
    fun insertMeal(meal: Meal)

    @Delete
    fun deleteMeal(meal: Meal)
}
