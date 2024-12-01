package com.example.project_meal_test.data


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Meal(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val place: String,
    val foodName: String,
    val cost: Int,
    val review: String,
    var date: String,
    val type: String,
    val imageUri: String,
    val calories: Int
)
