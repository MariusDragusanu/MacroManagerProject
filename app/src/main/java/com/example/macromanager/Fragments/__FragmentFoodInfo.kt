package com.example.macromanager.Fragments

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import androidx.activity.addCallback
import androidx.core.widget.doAfterTextChanged
import com.example.macromanager.Adapters.__Serving
import com.example.macromanager.CustomViews.__PieChart
import com.example.macromanager.Entity.__Food
import com.example.macromanager.Listeners.FragmentListener.__FragmentFoodInfoListener
import com.example.macromanager.Object.__ColorPallete
import com.example.macromanager.Object.__FoodCategory
import com.example.macromanager.R
import com.example.myapplication.Object.__Utility
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class __FragmentFoodInfo(val food:__Food,val mealList:MutableList<String>, val dayIndex:Int) : Fragment() {
private lateinit var listener: __FragmentFoodInfoListener



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val callback =
            requireActivity().onBackPressedDispatcher.addCallback(this.viewLifecycleOwner) {
                listener.detachFragmentFoodInfo(this@__FragmentFoodInfo)
            }

        val view = inflater.inflate(R.layout.fragment_food_info, container, false)

        val totalProtein = food.nProteins
        val totalFat = food.nFats
        val totalCarbs = food.nCarbs
        val totalCalories = totalProtein * 4f + totalFat * 9f + totalCarbs * 4f

        val pcMacros = view.findViewById<__PieChart>(R.id.frg_food_info_pieChartMacros)
        pcMacros.setEntries(
            arrayListOf(
                Pair("Protein", totalProtein.toFloat() * 4f),
                Pair("Fat", totalFat.toFloat() * 8.8f),
                Pair("Carbs", totalCarbs.toFloat() * 4f)
            )
        )
        val stProtein = "${String.format("%.1f", totalProtein)}g protein"
        val stFat = "${String.format("%.1f", totalFat)}g fat"
        val stCarbs = "${String.format("%.1f", totalCarbs)}g carbs"
        val stTotal = "${totalCalories.toInt()} Kcal in 100 ${food.UnitOfMeasurement}s"
        val tvProtein = view.findViewById<TextView>(R.id.frg_food_info_tvProtein)
        tvProtein.text = stProtein
        val tvFat = view.findViewById<TextView>(R.id.frg_food_info_tvFat)
        tvFat.text = stFat
        val tvCarbs = view.findViewById<TextView>(R.id.frg_food_info_tvCarbs)
        tvCarbs.text = stCarbs
        val tvTotal = view.findViewById<TextView>(R.id.frg_food_info_tvCalories)
        tvTotal.text = stTotal
        val tvName = view.findViewById<TextView>(R.id.frg_food_info_tvName)
        val tvBrand = view.findViewById<TextView>(R.id.frg_food_info_tvBrand)
        tvName.text = food.Name
        tvBrand.text = food.Brand

        val icon = view.findViewById<ImageView>(R.id.frg_food_info_ivIcon)
        setIcon(icon, food)
        prepareChipGroup(view)
        val ilQuantity = view.findViewById<TextInputLayout>(R.id.frg_food_info_ilQuantity)
        val etQuantity = view.findViewById<TextInputEditText>(R.id.frg_food_info_etQuantity)
        val ilServing = view.findViewById<TextInputLayout>(R.id.frg_food_info_ilServing)
        val actvServing = view.findViewById<AutoCompleteTextView>(R.id.frg_food_info_actvServing)
        val ilMeal = view.findViewById<TextInputLayout>(R.id.frg_food_info_ilMeal)
        val actvMeal = view.findViewById<AutoCompleteTextView>(R.id.frg_food_info_actvMeal)
        val btnAddFood = view.findViewById<Button>(R.id.frg_food_info_btnAdd)
        etQuantity.setText(String.format("%.1f",food.Quantity))
        actvMeal.setText(mealList.last())
        var cond1 = false
        var cond2 = false
        var cond3 = false
        var quantity = 1.0;
        val servingNameList = mutableListOf<String>()
        for (servings in food.servingList) {
            servingNameList.add(servings.Name)
        }
        val servingAdapter =
            ArrayAdapter(requireContext(), R.layout.dropdown_generic_layout, servingNameList)
        val mealNameAdapter =
            ArrayAdapter(requireContext(), R.layout.dropdown_generic_layout, this.mealList)
        actvMeal.setAdapter(mealNameAdapter)
        actvServing.setAdapter(servingAdapter)
        etQuantity.doAfterTextChanged {
            if (etQuantity.text!!.isNotEmpty()) {
                cond1 = true
                quantity = etQuantity.text.toString().toDouble()
                btnAddFood.visibility = VISIBLE
            } else {
                btnAddFood.visibility = INVISIBLE
            }
        }
        btnAddFood.setOnClickListener {
            var servingSelected = __Serving()

            for (serving in food.servingList) {
                if (serving.Name == actvServing.text.toString()) {
                    servingSelected = serving
                    cond2 = true
                }
            }
            if (cond2 and cond1) {
                food.Quantity = quantity * servingSelected.quantity
            }
            cond1 = onUserInteraction(ilQuantity, etQuantity)
            cond2 = onUserInteraction(ilServing, actvServing)
            cond3 = onUserInteraction(ilMeal, actvMeal)
            if (cond1 and cond2 and cond3) {
                listener.onFoodAdded(food, dayIndex,actvMeal.text.toString())
                 listener.detachFragmentFoodInfo(this)
            }

        }
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
    fun prepareChipGroup(view:View) {
        val chipGroup = view.findViewById<ChipGroup>(R.id.chipGroup)
        val booleanArray = mutableListOf<Boolean>(
            __Utility.isCarbDense(food),
            __Utility.isFatDense(food),
            __Utility.isFiberSource(food),
            __Utility.isHighSourceOfProtein(food),
            __Utility.isLeanProteinSource(food),
            __Utility.isLowDenseCaloric(food),
            __Utility.isVeganFriendly(food),
            __Utility.isDenseCaloric(food),
            __Utility.hasPoorMacros(food)
        )
        if(booleanArray[0]){
            chipGroup.addView(createCipWithText("Carb Dense"))
        }
        if(booleanArray[1]){
            chipGroup.addView(createCipWithText("Fat Dense"))
        }
        if(booleanArray[2]){
            chipGroup.addView(createCipWithText("Fiber Source"))
        }
        if(booleanArray[3]){
            chipGroup.addView(createCipWithText("Protein Dense"))
        }
        if(booleanArray[4]){
            chipGroup.addView(createCipWithText("Lean source of protein"))
        }
        if(booleanArray[5]){
            chipGroup.addView(createCipWithText("Low-Dense food"))
        }
        if(booleanArray[6]){
            chipGroup.addView(createCipWithText("Vegan Friendly!"))
        }
        if(booleanArray[7]){
            chipGroup.addView(createCipWithText("High in Calories"))
        }
        if(booleanArray[8]){
            chipGroup.addView(createCipWithText("Poor Macro Distribution"))
        }

    }
    fun createCipWithText(caption:String):Chip{
        val chip=Chip(requireContext())
        chip.text=caption
        chip.chipBackgroundColor= resources.getColorStateList(R.color.colorPrimary2)
        chip.setTextColor(resources.getColor(R.color.colorAccent))
       return chip
    }
    fun onUserInteraction(inputLayout: TextInputLayout,inputEditText: EditText):Boolean{
        if(inputEditText.text!!.isEmpty()){
            inputLayout.setHelperTextColor(resources.getColorStateList(R.color.red))
        }else{
            inputLayout.setHelperTextColor(resources.getColorStateList(R.color.colorHint))
        }
        return inputEditText.text!!.isNotEmpty()
    }
}