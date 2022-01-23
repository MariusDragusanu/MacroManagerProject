package com.example.macromanager.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.addCallback
import com.example.macromanager.CustomViews.__PieChart
import com.example.macromanager.Entity.__Food
import com.example.macromanager.Listeners.FragmentListener.__FragmentFoodInfoListener
import com.example.macromanager.Object.__FoodCategory
import com.example.macromanager.R
import com.example.myapplication.Object.__Utility


class __FragmentFoodInfo(val food:__Food,mealList:MutableList<String>) : Fragment() {
private lateinit var listener: __FragmentFoodInfoListener



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this.viewLifecycleOwner) {
            listener.detachFragmentFoodInfo(this@__FragmentFoodInfo)
        }

        val view=inflater.inflate(R.layout.fragment_food_info, container, false)

            val totalProtein= food.nProteins
        val totalFat=food.nFats
        val totalCarbs=food.nCarbs
        val totalCalories= totalProtein*4f +totalFat*9f+ totalCarbs*4f
        val proteinPercent=((totalProtein*4f/totalCalories)*100f).toInt()
        val carbPercent=((totalCarbs*4f/totalCalories)*100f).toInt()
        val fatPercent=((totalFat*9f/totalCalories)*100f).toInt()
        val pcMacros=view.findViewById<__PieChart>(R.id.frg_food_info_pieChartMacros)
        pcMacros.setEntries( arrayListOf(Pair("Protein",totalProtein.toFloat()*4f),Pair("Fat",totalFat.toFloat()*8.8f),
                Pair("Carbs",totalCarbs.toFloat()*4f)))
        val stProtein="${String.format("%.1f",totalProtein)}g protein"
        val stFat="${String.format("%.1f",totalFat)}g fat"
        val stCarbs="${String.format("%.1f",totalCarbs)}g carbs"
        val stTotal="${totalCalories.toInt()} Kcal in 100 ${food.UnitOfMeasurement}s"
        val tvProtein=view.findViewById<TextView>(R.id.frg_food_info_tvProtein)
        tvProtein.text=stProtein
        val tvFat=view.findViewById<TextView>(R.id.frg_food_info_tvFat)
        tvFat.text=stFat
        val tvCarbs=view.findViewById<TextView>(R.id.frg_food_info_tvCarbs)
        tvCarbs.text=stCarbs
        val tvTotal=view.findViewById<TextView>(R.id.frg_food_info_tvCalories)
        tvTotal.text=stTotal
         val tvName=view.findViewById<TextView>(R.id.frg_food_info_tvName)
        val tvBrand=view.findViewById<TextView>(R.id.frg_food_info_tvBrand)
        tvName.text=food.Name
        tvBrand.text=food.Brand
        val icon=view.findViewById<ImageView>(R.id.frg_food_info_ivIcon)
        setIcon(icon,food)
        return view
    }

    fun setListener(newListener: __FragmentFoodInfoListener) {
        this.listener=newListener

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
        if(currentFood.Category== __FoodCategory.Egg){
            foodIcon.setBackgroundResource(R.drawable.icon_food_egg)
        }
        if(currentFood.Category== __FoodCategory.SeaFood){
            foodIcon.setBackgroundResource(R.drawable.icon_food_seafood)
        }
    }
}