package com.example.macromanager.Listeners.FragmentListener

import com.example.macromanager.Entity.__Food
import com.example.macromanager.Entity.__Meal
import com.example.macromanager.Fragments.__FragmentFoodDesigner

interface __FragmentMainListener {
     fun attachFragmentFoodDesigner()
     fun detachFragmentFoodDesigner(fragment: __FragmentFoodDesigner)
     fun attachFragmentMealInfo(currentMeal: __Meal)
     fun attachFragmentFoodInfo(currentFood: __Food,mealList:MutableList<String>)

}
