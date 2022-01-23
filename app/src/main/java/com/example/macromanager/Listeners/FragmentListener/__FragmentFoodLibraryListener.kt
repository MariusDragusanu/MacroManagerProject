package com.example.macromanager.Listeners.FragmentListener

import com.example.macromanager.Entity.__Food
import com.example.macromanager.Fragments.__FragmentFoodDesigner

interface __FragmentFoodLibraryListener {
    fun attachFragmentFoodDesigner()
    fun detachFragmentFoodDesigner(fragment: __FragmentFoodDesigner)
    fun attachFragmentFoodInfo(currentFood:__Food,mealList:MutableList<String>)

}