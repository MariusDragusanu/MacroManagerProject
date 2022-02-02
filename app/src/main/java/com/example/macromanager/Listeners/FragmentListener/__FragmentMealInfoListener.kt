package com.example.macromanager.Listeners.FragmentListener

import androidx.fragment.app.Fragment
import com.example.macromanager.Entity.__Food
import com.example.macromanager.Entity.__Meal
import com.example.macromanager.Fragments.__FragmentMealInfo

interface __FragmentMealInfoListener {
fun detachFragmentMealInfo(fragment:__FragmentMealInfo)
fun onAddFoodToMeal(fragment: __FragmentMealInfo,dayIndex:Int,mealName:String)
    fun attachFragmentFoodInfo(currentFood: __Food,currentMeal:String)
    fun getCurrentMeal(currentMeal: __Meal)





}
