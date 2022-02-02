package com.example.myapplication.Object


import android.text.TextUtils
import com.example.macromanager.Entity.__Food
import com.example.macromanager.Entity.__Meal
import com.example.macromanager.Object.__FoodCategory
import com.example.myapplication.Entity.__Date
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.time.Duration
import java.util.*

object __Utility {
    const val PI=3.14159265359f
    fun clampValue(min:Int, value:Int, max:Int):Int {
        var toBeReturned=0
       if(value>min) toBeReturned=value
        if(value<max) toBeReturned=value
        if(value<min) toBeReturned=min
        if(value>max) toBeReturned=max

    return toBeReturned
    }
    fun isLeapYear(date: __Date):Int{
        var year=date.year_number
        if(date.month_number<=2 ) {
            year--
        }
        return (year/4-year/100+year/400)
    }
    fun computeTime(dateStart:__Date,dateEnd:__Date):Int{
       val lenghOfMonthList= listOf<Int>(31,28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)

        var n1=dateStart.year_number*365+dateStart.day_number
        var result=0
        if(dateStart.month_name!=dateEnd.month_name) {
            for (i in 0 until dateStart.month_number) {
                n1 += lenghOfMonthList[i]
            }
            n1 += isLeapYear(dateStart)

            var n2 = dateEnd.year_number * 365 + dateEnd.day_number
            for (i in 0 until dateEnd.month_number) {
                n2 += lenghOfMonthList[i]
            }
            n2 += isLeapYear(dateEnd)
            result=n2-n1
        }
        else{
            result=dateEnd.day_number-dateStart.day_number
        }
        return result
    }
    fun interpolateValue(x1:Float,y1:Float,x:Float,x2:Float,y2:Float):Float{
return (y1+((x-x1)*(y2-y1)/(x2-x1)))
    }
    fun checkEmailFormat(enteredText:String):Boolean{
        return !TextUtils.isEmpty(enteredText) && android.util.Patterns.EMAIL_ADDRESS.matcher(enteredText).matches()
    }
    fun getTotalProteinGrams(element:__Meal):Float{
        var result=0.0f

            for(food in element.getList()) {
                result += food.nProteins.toFloat() * food.Quantity.toFloat()
            }
            return result

    }
    fun getTotalCarbsGrams(element:__Meal):Float{
        var result=0.0f

        for(food in element.getList()) {
            result += food.nCarbs.toFloat() * food.Quantity.toFloat()
        }
        return result

    }
    fun getTotalFatGrams(element:__Meal):Float{
        var result=0.0f

        for(food in element.getList()) {
            result += food.nFats.toFloat() * food.Quantity.toFloat()
        }
        return result

    }
    fun calculateTotalCalories(element:__Meal):Float{
        var result=0f
        for(food in element.getList()){
            result+=(food.Quantity*food.caloriesIn100UM/100f).toFloat()
        }
        return result
    }
    fun checkInput(layout:TextInputLayout,editText:TextInputEditText){

    }
    fun isFiberSource(food: __Food):Boolean{
            return food.nFiber>2
    }
    fun isLeanProteinSource(food: __Food):Boolean{
        var result=false
        if((((food.nProteins*4f)/food.caloriesIn100UM)>0.45) and ((food.nFats*9f)/food.caloriesIn100UM<0.30f)){
            result=true
        }
        return result
    }
    fun isLowDenseCaloric(food: __Food):Boolean{
        var result=false
        if((food.caloriesIn100UM/100f)<0.8){
            result=true
        }
        return result
    }
    fun isVeganFriendly(food: __Food):Boolean{
        var result=false
        if((food.Category==__FoodCategory.Fruits)||(food.Category==__FoodCategory.Grains)||(food.Category==__FoodCategory.Vegetables)||(food.Category==__FoodCategory.Soda)){
            result=true
        }

        return result
    }
    fun isHighSourceOfProtein(food: __Food):Boolean{
        return (food.nProteins>15f)
    }
    fun isCarbDense(food: __Food):Boolean{
        var result=false
        if(((food.nCarbs*4f)/food.caloriesIn100UM)>0.45){
            result=true
        }
        return result
    }
    fun isFatDense(food: __Food):Boolean{
        var result=false
        if(food.nFats*8.8f/food.caloriesIn100UM>0.35){
            result=true
        }
        return result
    }
    fun isDenseCaloric(food:__Food):Boolean{
        return (food.caloriesIn100UM>400)
    }
    fun hasPoorMacros(food:__Food):Boolean{
        var result=false
     if(isFatDense(food) and isCarbDense(food)){
         result=true
     }
        return result
    }

    fun getTotalFiberGrams(currentMeal: __Meal): Float {
        var result=0f
        for(food in currentMeal.getList()){
            result+=(food.nFiber*food.Quantity).toFloat()
        }
        return result
    }
}


