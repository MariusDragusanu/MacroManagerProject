package com.example.macromanager.Entity

import __Record
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import com.example.myapplication.Entity.__Date
import com.example.myapplication.Entity.__UserBiometrics
import com.example.myapplication.Entity.__UserDietPlan
import com.example.myapplication.Object.__Utility
import com.google.firebase.auth.FirebaseAuth

class __User3(val UID:String?=FirebaseAuth.getInstance().currentUser?.uid,
               var userAccountInformation: __UserAccountInformation= __UserAccountInformation(FirebaseAuth.getInstance().currentUser),
               var userBiometrics: __UserBiometrics= __UserBiometrics(178f,75f,19,true,1.55f),
               var userDietPlan: __UserDietPlan= __UserDietPlan(0.35f,0.25f,0.4f)){
     var mealSavedList = mutableListOf<__Meal>()
     var mealCurrentList = mutableListOf<__Meal>()
     var foodFavoriteList = mutableListOf<__Food>()
     var foodRecentList = mutableListOf<__Food>()
     var dayHistory:MutableList<__Day> = mutableListOf()






fun retrieveDay(index:Int=0):__Day{

    return dayHistory[index]
}
fun retrieveCurrentDayMeals():MutableList<__Meal>{
    var result= mutableListOf<__Meal>()
    for(entry in dayHistory){
        if(entry.dateOfDay.minus(__Date())==0){
            result=entry.mealList
        }
    }
    return result
}
    fun getMealListTitle(): MutableList<String> {
        val list = mutableListOf<String>()
        for (meal in mealCurrentList)list.add(meal.getTitle())
       return list
    }
    fun hasActiveDietPlan(): Boolean {
        var result = false
        if (__Utility.computeTime(userAccountInformation.entryDate, userDietPlan.startDate) <= 0) {
            result = true
        }
        return result
    }
  fun isANewDay():Boolean{
      return(userAccountInformation.exitDate.day_number<userAccountInformation.entryDate.day_number)
  }
    fun addLastMealToHistory(){
        val day=__Day(mealCurrentList, __Date())
        if(dayHistory.size>31){
            dayHistory.clear()
        }
        dayHistory.add(day)
        mealCurrentList.clear()
    }

    fun GetCalories(): Int {
        return userDietPlan.calculateTotalCalories()
    }

    fun updateValues() {
        userBiometrics.onUpdate()
        userDietPlan.onUserInfoChanged(
            userBiometrics.baseMetabolicRate,
            userBiometrics.activityLevelCoefficient
        )
    }

    fun addRecentFood(newVal: __Food) {
        var ok = true
        for (food in foodRecentList) {
            if (food.Name.equals(newVal.Name, true) and food.Brand.equals(newVal.Brand, true)) {
                ok = false
            }
        }
        if (ok) {
            foodRecentList.add(0, newVal)
        }
    }

    fun addFavoriteFood(newVal: __Food) {
        var ok = true
        for (food in foodFavoriteList) {
            if (food.Name.equals(newVal.Name, true) and food.Brand.equals(newVal.Brand, true)) {
                ok = false
            }
        }
        if (ok) {
            foodFavoriteList.add(0, newVal)
        }
    }

    fun addMeal(newVal: __Meal) {
        mealCurrentList.add(0, newVal)
    }

    fun removeMeal(oldMeal: __Meal) {
        mealCurrentList.remove(oldMeal)
    }

    fun removeMeal(where: Int) {
        mealCurrentList.removeAt(where)
    }

    fun addFoodToMeal(mealName: String, newVal: __Food) {
        for (meal in mealCurrentList) {
            if (meal.getTitle().equals(mealName, true)) {
                meal.addFood(newVal)
            }
        }
    }

    fun updateMeal(newVal: __Meal) {

        for (index in 0 until mealCurrentList.size) {
            if (mealCurrentList[index].getTitle().equals(newVal.getTitle(), true)) {
                mealCurrentList.removeAt(index)
                mealCurrentList.add(index, newVal)
            }
        }
    }

    fun addNewWeight(value: Float) {
        this.userBiometrics!!.addNewWeightEntry(__Record(__Date(), value))
        Log.d("Test","It should be called once")
    }

    fun addMealToDay(currentMealIndex: Int,meal:__Meal) {
        dayHistory[currentMealIndex].mealList.add(meal)
        Log.d("Test","It should be called once")
    }
    fun addFoodToMeal(dayIndex:Int,food: __Food,mealName: String){
        for(meal in dayHistory[dayIndex].mealList){
            if(meal.getTitle()==mealName){
                meal.addFood(food)
                Log.d("Test","It should be called once")
            }
        }
    }
    fun updateMeal(dayIndex: Int,newMeal: __Meal){
        for(index in 0 until dayHistory[dayIndex].mealList.size){
            if(dayHistory[dayIndex].mealList[index].getTitle().equals(newMeal.getTitle(),true)){
                 dayHistory[dayIndex].mealList.removeAt(index)
                 dayHistory[dayIndex].mealList.add(index,newMeal)
            }
        }
    }
    fun removeMeal(dayIndex: Int,oldMeal: __Meal){
      dayHistory[dayIndex].mealList.remove(oldMeal)
    }

}