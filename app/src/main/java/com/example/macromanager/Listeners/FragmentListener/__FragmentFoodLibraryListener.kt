package com.example.macromanager.Listeners.FragmentListener

import com.example.macromanager.Entity.__Food
import com.example.macromanager.Fragments.__FragmentFoodDesigner
import com.example.macromanager.Fragments.__FragmentFoodInfo

interface __FragmentFoodLibraryListener {
    fun attachFragmentFoodDesigner()
    fun detachFragmentFoodDesigner(fragment: __FragmentFoodDesigner)
    fun attachFragmentFoodInfo(currentFood:__Food,dayIndex:Int,mealList:MutableList<String>)
    fun onFoodAddedToMeal(currentFood: __Food,mealName:String,dayIndex:Int)
    fun onFoodCreated()
    fun detachFragmentFoodInfo(fragment: __FragmentFoodInfo)

}