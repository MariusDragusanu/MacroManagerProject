package com.example.macromanager.Entity

import com.example.myapplication.Entity.__Date
import com.google.type.TimeOfDay

class __Day(var mealList: MutableList<__Meal> = mutableListOf(__Meal("Test")), var dateOfDay:__Date=__Date()) {
    fun createMealNameList():MutableList<String>{
        val list= mutableListOf<String>()
        for(meal in mealList){
            list.add(meal.getTitle())
        }
        return list
    }
}