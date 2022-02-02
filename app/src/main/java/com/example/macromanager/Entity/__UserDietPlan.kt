package com.example.myapplication.Entity

import android.util.Log
import com.example.myapplication.Object.__Utility

class __UserDietPlan( var proteinPercent:Float=0.3f,
                      var fatPercent:Float=0.2f,
                      var carbPercent:Float=0.5f) {
    var startDate: __Date = __Date()
    var endDate: __Date?= __Date()
    var baseCalories: Int = 0
    var weeklyProgress: Int = 50
    var cardio: Int = 0


    fun setNewGoalTimeframe(date1: __Date, date2: __Date) {
        startDate = date1
        endDate = date2
    }

    fun computeDailyCaloricDelta(): Int {

        val weightDelta = __Utility.interpolateValue(0f, -0.5f, weeklyProgress!! * 0.01f, 1f, 0.5f)
        val result = ((weightDelta * 8800f) / 7f).toInt()

        return result
    }


    fun calculateTotalCalories(): Int {


        return baseCalories+ cardio
    }

    fun onUserInfoChanged(newBmr: Float, newActivityLevel: Float) {
        this.baseCalories = (newBmr * newActivityLevel).toInt() + computeDailyCaloricDelta()

    }

}



