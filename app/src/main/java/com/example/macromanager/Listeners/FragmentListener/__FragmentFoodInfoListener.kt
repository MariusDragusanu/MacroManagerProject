package com.example.macromanager.Listeners.FragmentListener

import com.example.macromanager.Entity.__Food
import com.example.macromanager.Entity.__Meal
import com.example.macromanager.Fragments.__FragmentFoodInfo

interface __FragmentFoodInfoListener {
fun detachFragmentFoodInfo(fragment:__FragmentFoodInfo)
fun onFoodAdded(food:__Food,dayIndex:Int,mealName: String)
}
