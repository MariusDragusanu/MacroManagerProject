package com.example.macromanager.Listeners.FragmentListener

import com.example.macromanager.Entity.__Food
import com.example.macromanager.Entity.__Meal
import com.example.macromanager.Fragments.__FragmentFoodDesigner
import com.example.macromanager.Fragments.__FragmentFoodInfo
import com.example.macromanager.Fragments.__FragmentMealInfo

interface __FragmentMainListener {
    fun detachFragmentFoodInfo(fragment: __FragmentFoodInfo)
    fun onFoodAdded(food:__Food,mealName: String)
    fun detachFragmentMealInfo(fragment: __FragmentMealInfo)
    fun onAddFoodToMeal()
    fun attachFragmentMealInfo(currentMeal:__Meal,mealInfoListener: __FragmentMealInfoListener)
    fun attachFragmentFoodDesigner(fragmentFoodDesignerListener: __FragmentFoodDesignerListener)
    fun detachFragmentFoodDesigner(fragment: __FragmentFoodDesigner)
    fun attachFragmentFoodInfo(currentFood:__Food,mealList:MutableList<String>,__FragmentFoodInfoListener: __FragmentFoodInfoListener)
    fun onFoodAddedToMeal(currentFood: __Food,mealName:String)
    fun onFoodCreated()



}
