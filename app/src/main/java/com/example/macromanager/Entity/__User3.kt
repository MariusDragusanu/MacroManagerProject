package com.example.macromanager.Entity

import android.os.Parcel
import android.os.Parcelable
import com.example.myapplication.Entity.__UserBiometrics
import com.example.myapplication.Entity.__UserDietPlan
import com.google.firebase.auth.FirebaseAuth

class __User3(val UID:String?=FirebaseAuth.getInstance().currentUser?.uid,
               var userAccountInformation: __UserAccountInformation?= __UserAccountInformation(FirebaseAuth.getInstance().currentUser),
               var userBiometrics: __UserBiometrics?= __UserBiometrics(178f,75f,19,true,1.55f),
               var userDietPlan: __UserDietPlan?= __UserDietPlan(0.35f,0.25f,0.4f)){
     var mealSavedList = mutableListOf<__Meal>()
     var mealCurrentList = mutableListOf<__Meal>()
     var foodFavoriteList = mutableListOf<__Food>()
     var foodRecentList = mutableListOf<__Food>()





    fun GetCalories(): Int {
        return userDietPlan!!.calculateTotalCalories()
    }

    fun getMealListTitle(): MutableList<String> {
        val list = mutableListOf<String>()
        for (meal in mealCurrentList)list.add(meal.getTitle())
       return list
    }


}