package com.example.myapplication.Entity

import android.os.Parcel
import android.os.Parcelable
import com.example.macromanager.Entity.__Food
import com.example.macromanager.Entity.__Meal
import com.example.myapplication.Object.__Utility

/* class __User( private var email: String ="DefaultEmail@gmail.com")
{
    private   var recentFoodList:MutableList<__Food>? = mutableListOf()
    private   var favoriteFoodList:MutableList<__Food>? = mutableListOf()
    private   var mealList:MutableList<__Meal>?= mutableListOf()
    private   var entryDate:__Date?=__Date()
    private   var exitDate:__Date?= __Date()
    private   var userBiometrics:__UserBiometrics?= __UserBiometrics()
    private   var dietPlan:__UserDietPlan?= __UserDietPlan()




    fun setEmail(newVal: String){
        email=newVal
    }
    fun getEmail()=email
    fun getRecentFoodList()=recentFoodList
    fun getFavoriteFoodList()=favoriteFoodList
    fun getMealList()=mealList
    fun getEntryDate()=entryDate
    fun getExitDate()=exitDate
    fun getUserBiometrics()=userBiometrics
    fun  getDietPlan()=dietPlan
    fun setExitDate(newVal: __Date){
        this.exitDate=newVal
    }
    fun setEntryDate(newVal: __Date){
        this.entryDate=newVal
    }
    fun prepareForNewDay(){
        mealList!!.clear()
    }

    fun hasActiveDietPlan():Boolean{
        var result=false
        if(__Utility.computeTime(entryDate!!,dietPlan!!.getstartDate())<=0) {
            result = true
        }
        return result
    }
    fun GetCalories():Int{
     return dietPlan!!.getTotalCalories()
    }
    fun updateValues(){
        userBiometrics!!.onUpdate()
        dietPlan!!.onUserInfoChanged(userBiometrics!!.getBaseMetabolicRate()!!,userBiometrics!!.getActivityLevelCoefficient()!!)
    }

    fun addRecentFood(newVal:__Food){
       var ok=true
       for(food in recentFoodList!!){
           if(food.Name.equals(newVal.Name,true) and food.Brand.equals(newVal.Brand,true)){
               ok=false
               }
       }
       if(ok){
           recentFoodList!!.add(0,newVal)
       }
   }
    fun addFavoriteFood(newVal: __Food){
        var ok=true
        for(food in favoriteFoodList!!){
            if(food.Name.equals(newVal.Name,true) and food.Brand.equals(newVal.Brand,true)){
                ok=false
            }
        }
        if(ok){
            favoriteFoodList!!.add(0,newVal)
        }
    }
    fun addMeal(newVal: __Meal){
        mealList!!.add(0,newVal)
    }
    fun removeMeal(oldMeal: __Meal){
        mealList!!.remove(oldMeal)
    }
    fun removeMeal(where:Int){
        mealList!!.removeAt(where)
    }
    fun addFoodToMeal(mealName:String,newVal: __Food){
        for(meal in mealList!!){
            if(meal.getTitle().equals(mealName,true)){
                meal.addFood(newVal)
            }
        }
    }
    fun updateMeal(newVal: __Meal){
       for(index in 0 until mealList!!.size){
           if(mealList!![index].getTitle().equals(newVal.getTitle(),true)){
               mealList!!.removeAt(index)
               mealList!!.add(index,newVal)
           }
       }
    }
   fun addNewWeight(value: Float){
       this.userBiometrics!!.addNewWeightEntry(__Record(__Date(),value))
   }

    fun setUserBiometrics(biometrics: __UserBiometrics) {
        userBiometrics=biometrics
    }

    fun setDietPlan(dietPlan: __UserDietPlan) {
        this.dietPlan=dietPlan
    }





}*/