package com.example.macromanager.Adapters

import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.macromanager.Entity.__Food
import com.example.macromanager.Entity.__Meal
import com.example.macromanager.Entity.__User3
import com.example.macromanager.Object.__FoodCategory
import com.example.macromanager.R

class __FoodAdapterForLibrary(val foodList: MutableList<__Food>,val layout:Int):__GenericAdapter<__Food>(foodList,layout) {
    override fun onLongClickListener(view: View, any: __Food, position: Int): Boolean {
        return true
    }

    override fun onClick(view: View, t: __Food, position: Int) {

    }

    override fun onElementConfig(view: View, t: __Food, position: Int) {
        val tvTitle = view.findViewById<TextView>(R.id.entity_food_tvTitle)
        tvTitle.text=t.Name
        val tvBrand=view.findViewById<TextView>(R.id.entity_food_tvBrand)
        tvBrand.text=t.Brand
        val btnMore = view.findViewById<ImageButton>(R.id.entity_food_btnMore)
        val icon=view.findViewById<ImageView>(R.id.entity_food_ivCategory)
        setIcon(icon,t)
        btnMore.setOnClickListener {
            onExpandView(view,t,position)
        }
    }
    private fun onExpandView(view: View, t: __Food, position: Int){
        expandLayout(view,t,position)
        configLayout(view,t)

    }

    private fun configLayout(view: View, t: __Food) {

        val stProtein="${String.format("%.1f", t.nProteins)}g protein"
        val stFat="${String.format("%.1f",t.nFats)}g fat"
        val stCarbs="${String.format("%.1f",t.nCarbs/100f)}g carbs"
        val stTotal="${String.format("%.1f",t.caloriesIn100UM)} Kcal in ${t.UnitOfMeasurement}s "
        val tvProtein=view.findViewById<TextView>(R.id.entity_food_tvProtein)
        tvProtein.text=stProtein
        val tvFat=view.findViewById<TextView>(R.id.entity_food_tvFat)
        tvFat.text=stFat
        val tvCarbs=view.findViewById<TextView>(R.id.entity_food_tvCarbs)
        tvCarbs.text=stCarbs
        val tvTotal=view.findViewById<TextView>(R.id.entity_food_tvTotal)
        tvTotal.text=stTotal
    }

    fun expandLayout(view: View, t: __Food, position: Int){
        val constraintLayout = view.findViewById<ConstraintLayout>(R.id.entity_food_clMoreInfo)
        if (constraintLayout.visibility == View.GONE) {
            constraintLayout.visibility = View.VISIBLE
        } else {
            constraintLayout.visibility = View.GONE
        }
    }
    private fun setIcon(foodIcon: ImageView, currentFood: __Food) {
        if(currentFood.Category== __FoodCategory.Meat)
        {
            foodIcon.setBackgroundResource(R.drawable.icon_food_meat)
        }
        if(currentFood.Category== __FoodCategory.Dairy)
        {
            foodIcon.setBackgroundResource(R.drawable.icon_food_dairy)
        }
        if(currentFood.Category== __FoodCategory.Pastry)
        {
            foodIcon.setBackgroundResource(R.drawable.icon_food_bread)
        }
        if(currentFood.Category== __FoodCategory.Grains)
        {
            foodIcon.setBackgroundResource(R.drawable.icon_food_grains)
        }
        if(currentFood.Category== __FoodCategory.Beverage)
        {
            foodIcon.setBackgroundResource(R.drawable.icon_food_beverage)
        }
        if(currentFood.Category== __FoodCategory.Soda)
        {
            foodIcon.setBackgroundResource(R.drawable.icon_food_soda)
        }
        if(currentFood.Category== __FoodCategory.Fruits)
        {
            foodIcon.setBackgroundResource(R.drawable.icon_food_fruit)
        }
        if(currentFood.Category== __FoodCategory.Fitness_Supplements)
        {
            foodIcon.setBackgroundResource(R.drawable.icon_fitness_suplements)
        }
        if(currentFood.Category== __FoodCategory.Cakes)
        {
            foodIcon.setBackgroundResource(R.drawable.icon_food_cake)
        }
        if(currentFood.Category== __FoodCategory.Snacks)
        {
            foodIcon.setBackgroundResource(R.drawable.icon_food_candy)
        }
        if(currentFood.Category== __FoodCategory.Vegetables)
        {
            foodIcon.setBackgroundResource(R.drawable.icon_food_vegetable)
        }
        if(currentFood.Category== __FoodCategory.FastFood)
        {
            foodIcon.setBackgroundResource(R.drawable.icon_fast_food)
        }
        if(currentFood.Category==__FoodCategory.Egg){
            foodIcon.setBackgroundResource(R.drawable.icon_food_egg)
        }
        if(currentFood.Category==__FoodCategory.SeaFood){
            foodIcon.setBackgroundResource(R.drawable.icon_food_seafood)
        }
    }
}