package com.example.project_meal_test.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Meal::class], version = 2, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mealDao(): MealDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null


        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "meal_database"
                )   .fallbackToDestructiveMigration() // 기존 데이터를 삭제하고 새 스키마로 재생성
                    .allowMainThreadQueries() // 개발 환경에서만 사용! 메인 스레드에서 데이터베이스 접근 허용
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }
}
