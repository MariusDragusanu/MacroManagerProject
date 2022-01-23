package com.example.macromanager.Adapters

import android.view.View
import android.view.View.*
import android.widget.ImageButton
import android.widget.ProgressBar

import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

import com.example.macromanager.Entity.__Meal
import com.example.macromanager.Listeners.__AdapterMealListener
import com.example.macromanager.R
import com.example.myapplication.Object.__Utility


class __MealAdapter(val mealList:MutableList<__Meal>, layoutId:Int):__GenericAdapter<__Meal>(mealList,layoutId) {
    private lateinit var listener:__AdapterMealListener
    fun setListener(newListener: __AdapterMealListener){
        this.listener=newListener
    }
    override fun onLongClickListener(view: View, any: __Meal, position: Int): Boolean {
        return true
    }

    override fun onClick(view: View, t: __Meal, position: Int) {

    }

    override fun onElementConfig(view: View, t: __Meal, position: Int) {
        val tvTitle = view.findViewById<TextView>(R.id.entity_meal_tvTitle)
        tvTitle.text=t.getTitle()
        val btnMore = view.findViewById<ImageButton>(R.id.entity_meal_btnMore)
        val btnAdd=view.findViewById<ImageButton>(R.id.entity_meal_btnAddFood)
        btnAdd.setOnClickListener {
            listener.onMealClicked(t)
        }
        btnMore.setOnClickListener {
onExpandView(view,t,position)
        }
    }
    private fun onExpandView(view: View, t: __Meal, position: Int){
        expandLayout(view,t,position)
        setTextForProgressBars(view, t, position)
    }
    fun expandLayout(view: View, t: __Meal, position: Int){
        val constraintLayout = view.findViewById<ConstraintLayout>(R.id.entity_meal_clMoreInfo)
        if (constraintLayout.visibility == GONE) {
            constraintLayout.visibility = VISIBLE
        } else {
            constraintLayout.visibility = GONE
        }
    }
    fun setTextForProgressBars(view: View, t: __Meal, position: Int){
        val barProtein=view.findViewById<ProgressBar>(R.id.entity_meal_pbProtein)
        val barFat=view.findViewById<ProgressBar>(R.id.entity_meal_pbFat)
        val barCarbs=view.findViewById<ProgressBar>(R.id.entity_meal_pbCarbs)
        val totalProtein=__Utility.getTotalProteinGrams(t)
        val totalFat=__Utility.getTotalFatGrams(t)
        val totalCarbs=__Utility.getTotalCarbsGrams(t)
        val totalCalories=__Utility.calculateTotalCalories(t)
        val proteinPercent=((totalProtein*4f/totalCalories)*100f).toInt()
        val carbPercent=((totalCarbs*4f/totalCalories)*100f).toInt()
        val fatPercent=((totalFat*9f/totalCalories)*100f).toInt()
        barProtein.setProgress(proteinPercent,true)
        barCarbs.setProgress(carbPercent,true)
        barFat.setProgress(fatPercent,true)
        val stProtein="${String.format("%.1f",totalProtein/100f)}g protein"
        val stFat="${String.format("%.1f",totalFat/100f)}g fat"
        val stCarbs="${String.format("%.1f",totalCarbs/100f)}g carbs"
        val stTotal="${totalCalories.toInt()} Kcal "
        val tvProtein=view.findViewById<TextView>(R.id.entity_meal_tvProtein)
        tvProtein.text=stProtein
        val tvFat=view.findViewById<TextView>(R.id.entity_meal_tvFat)
        tvFat.text=stFat
        val tvCarbs=view.findViewById<TextView>(R.id.entity_meal_tvCarbs)
        tvCarbs.text=stCarbs
        val tvTotal=view.findViewById<TextView>(R.id.entity_meal_tvTotal)
        tvTotal.text=stTotal

    }
}

