package com.example.macromanager.Entity

import __Record
import com.example.myapplication.Entity.*
import com.example.myapplication.Object.__Utility
import com.google.firebase.auth.FirebaseUser

class __User2( firebaseUser: FirebaseUser?=null) {

  /*  var UID=firebaseUser?.uid
    private var accountInformation:__UserAccountInformation?=null
    private var userBiometrics : __UserBiometrics?=null
    private var userDietPlan : __UserDietPlan?=null
    private val todayMealList = mutableListOf<__Meal>()
    private val mealList = mutableListOf<__Meal>()
    private val recentFoodList = mutableListOf<__Food>()
    private val favoriteFoodList = mutableListOf<__Food>()





    fun getRecentFoodList() = recentFoodList
    fun getFavoriteFoodList() = favoriteFoodList
    fun getMealList() = mealList
   fun getAccountInfo()=accountInformation
    fun getUserBiometrics() = userBiometrics
    fun getDietPlan() = userDietPlan


    fun prepareForNewDay() {
        todayMealList.clear()
    }

    fun hasActiveDietPlan(): Boolean {
        var result = false
        if (__Utility.computeTime(accountInformation!!.getEntryDate()!!, userDietPlan!!.startDate!!) <= 0) {
            result = true
        }
        return result
    }

    fun GetCalories(): Int {
        return userDietPlan!!.calculateTotalCalories()
    }

    fun updateValues() {
        userBiometrics!!.onUpdate()
        userDietPlan!!.onUserInfoChanged(
            userBiometrics!!.baseMetabolicRate!!,
            userBiometrics!!.activityLevelCoefficient!!
        )
    }

    fun addRecentFood(newVal: __Food) {
        var ok = true
        for (food in recentFoodList) {
            if (food.Name.equals(newVal.Name, true) and food.Brand.equals(newVal.Brand, true)) {
                ok = false
            }
        }
        if (ok) {
            recentFoodList.add(0, newVal)
        }
    }

    fun addFavoriteFood(newVal: __Food) {
        var ok = true
        for (food in favoriteFoodList) {
            if (food.Name.equals(newVal.Name, true) and food.Brand.equals(newVal.Brand, true)) {
                ok = false
            }
        }
        if (ok) {
            favoriteFoodList.add(0, newVal)
        }
    }

    fun addMeal(newVal: __Meal) {
        todayMealList.add(0, newVal)
    }

    fun removeMeal(oldMeal: __Meal) {
        todayMealList.remove(oldMeal)
    }

    fun removeMeal(where: Int) {
        todayMealList.removeAt(where)
    }

    fun addFoodToMeal(mealName: String, newVal: __Food) {
        for (meal in todayMealList) {
            if (meal.getTitle().equals(mealName, true)) {
                meal.addFood(newVal)
            }
        }
    }

    fun updateMeal(newVal: __Meal) {
        for (index in 0 until todayMealList.size) {
            if (todayMealList[index].getTitle().equals(newVal.getTitle(), true)) {
                todayMealList.removeAt(index)
                todayMealList.add(index, newVal)
            }
        }
    }

    fun addNewWeight(value: Float) {
        this.userBiometrics!!.addNewWeightEntry(__Record(__Date(), value))
    }
    */
}
