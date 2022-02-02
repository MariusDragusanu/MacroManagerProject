package com.example.macromanager.Listeners

import com.example.macromanager.Entity.__Meal

interface __AdapterMealListener {
fun onMealClicked(currentMeal: __Meal)
    fun clearCurrentMeal(meal: __Meal)

}