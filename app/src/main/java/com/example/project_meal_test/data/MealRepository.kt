package com.example.project_meal_test.data


class MealRepository(private val mealDao: MealDao) {
    fun getMeals() = mealDao.getAllMeals()
    fun addMeal(meal: Meal) = mealDao.insertMeal(meal)
    fun deleteMeal(meal: Meal) = mealDao.deleteMeal(meal)
}
