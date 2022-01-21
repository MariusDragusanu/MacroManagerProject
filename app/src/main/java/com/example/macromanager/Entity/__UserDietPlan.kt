package com.example.myapplication.Entity

import android.util.Log
import com.example.myapplication.Object.__Utility

class __UserDietPlan( var proteinPercent:Float?=null,
                      var fatPercent:Float?=null,
                      var carbPercent:Float?=null) {
       private var startDate: __Date?= __Date()
       private var endDate: __Date?= __Date()
       private var baseCalories:Int?=0
       private var weeklyProgress:Int?=50
       private var cardio:Int?=0




 fun setNewGoalTimeframe(date1:__Date,date2: __Date){
     startDate=date1
     endDate=date2
 }
    fun computeDailyCaloricDelta():Int{

        val weightDelta=__Utility.interpolateValue(0f,-0.5f,weeklyProgress!!*0.01f,1f,0.5f)
        val result=((weightDelta*8800f)/7f).toInt()

        return result
    }


    fun setWeeklyProgress(newVal: Int){
        weeklyProgress=newVal

    }
    fun setBaseCalories(value:Int){
        baseCalories=value
    }
    fun setCardio(newVal: Int){
        cardio=newVal
    }
    fun getCardio()=cardio!!

    fun calculateTotalCalories(): Int {



       return baseCalories!!+cardio!!
    }
    fun onUserInfoChanged(newBmr:Float,newActivityLevel:Float){
        this.baseCalories=(newBmr*newActivityLevel).toInt()+computeDailyCaloricDelta()

    }

    fun getWeeklyProgress(): Int {
return  this.weeklyProgress!!
    }

    fun getStartDate() =startDate!!
    fun getEndDate()=endDate!!

    }



