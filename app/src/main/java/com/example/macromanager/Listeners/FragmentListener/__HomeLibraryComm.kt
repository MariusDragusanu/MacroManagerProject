package com.example.macromanager.Listeners.FragmentListener
//primul cuv=sursa
//al doilea cuv=destinatia
interface __HomeLibraryComm {
    fun getDaySelectedInfo(dayIndex:Int,mealNameList:MutableList<String>)
    fun onAddMeal(dayIndex: Int,mealName:String)
}