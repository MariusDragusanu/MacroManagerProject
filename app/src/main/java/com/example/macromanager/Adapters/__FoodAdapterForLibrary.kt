package com.example.macromanager.Adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.macromanager.Entity.__Food
import com.example.macromanager.Listeners.__AdapterFoodListener
import com.example.macromanager.Object.__FoodCategory
import com.example.macromanager.R

class __FoodAdapterForLibrary(val foodList: MutableList<__Food>,val layout:Int):__GenericAdapter<__Food>(foodList,layout) {
    private lateinit var listener:__AdapterFoodListener
    fun setListener(newListener: __AdapterFoodListener){
        this.listener=newListener
    }
    override fun onLongClickListener(view: View, any: __Food, position: Int): Boolean {
        return true
    }

    override fun onClick(view: View, t: __Food, position: Int) {
listener.onFoodItemClicked(t)
    }

    override fun onElementConfig(view: View, t: __Food, position: Int) {
        val tvTitle = view.findViewById<TextView>(R.id.entity_food_tvTitle)
        tvTitle.text=t.Name
        val tvBrand=view.findViewById<TextView>(R.id.entity_food_tvBrand)
        tvBrand.text=t.Brand
        val icon=view.findViewById<ImageView>(R.id.entity_food_ivCategory)
        setIcon(icon,t)

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