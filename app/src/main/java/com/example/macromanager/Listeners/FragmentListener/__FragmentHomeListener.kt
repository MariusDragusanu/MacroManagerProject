package com.example.macromanager.Listeners.FragmentListener

import com.example.macromanager.Entity.__Food
import com.example.macromanager.Entity.__Meal
import com.example.macromanager.Entity.__User3
import com.example.macromanager.Fragments.__FragmentMealInfo

interface __FragmentHomeListener {
    fun attachFragmentMealInfo(currentMeal:__Meal,dayIndex: Int)
    fun detachFragmentMealInfo(fragment: __FragmentMealInfo)
    fun onAddFoodToMeal(fragment: __FragmentMealInfo,dayIndex: Int,mealName:String)
    fun updateCurrentUser(user3: __User3)
    fun attachFragmentFoodInfo(currentFood: __Food,currentMeal:String, dayIndex:Int)



}