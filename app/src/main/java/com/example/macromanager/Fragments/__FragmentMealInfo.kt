package com.example.macromanager.Fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.*
import androidx.activity.addCallback
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.macromanager.Adapters.__FoodAdapterForLibrary
import com.example.macromanager.Adapters.__Serving
import com.example.macromanager.CustomViews.__PieChart
import com.example.macromanager.Entity.__Food
import com.example.macromanager.Entity.__Meal
import com.example.macromanager.Listeners.FragmentListener.__FragmentMealInfoListener
import com.example.macromanager.Listeners.__AdapterFoodListener
import com.example.macromanager.R
import com.example.myapplication.Object.__Utility
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class __FragmentMealInfo(val currentMeal: __Meal,val dayIndex:Int) : Fragment(),__AdapterFoodListener {
    private lateinit var pcGraph: __PieChart
    private lateinit var tvFiber: TextView
    private lateinit var listener: __FragmentMealInfoListener
fun setListener(newListener: __FragmentMealInfoListener){
    this.listener=newListener
}

    private lateinit var tvProtein:TextView
    private lateinit var tvFat:TextView
    private lateinit var tvCarb:TextView
    private lateinit var tvTotalCalories:TextView
    private lateinit var ilMealName:TextInputLayout
    private lateinit var ilMealCategory:TextInputLayout
    private lateinit var etMealName:TextInputEditText
    private lateinit var actvMealCategory:AutoCompleteTextView
   private lateinit var btnSave:Button
    private lateinit var btnAddFood:AppCompatImageButton
    private lateinit var btnSaveMeal:AppCompatButton
    private lateinit var rvFood:RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_meal_info, container, false)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this.viewLifecycleOwner) {
            listener.detachFragmentMealInfo(this@__FragmentMealInfo)
        }
            initViews(view)
             setTexts()

        return view
    }

    fun onUserInteraction(inputLayout: TextInputLayout, inputEditText: EditText):Boolean{
        if(inputEditText.text!!.isEmpty()){
            inputLayout.setHelperTextColor(resources.getColorStateList(R.color.red))
        }else{
            inputLayout.setHelperTextColor(resources.getColorStateList(R.color.colorHint))
        }
        return inputEditText.text!!.isNotEmpty()
    }
    fun initViews(view:View) {
        pcGraph = view.findViewById(R.id.__PieChart)
        tvProtein = view.findViewById(R.id.frg_meal_info_tvProtein)
        tvFat = view.findViewById(R.id.frg_meal_info_tvFat)
        tvCarb = view.findViewById(R.id.frg_meal_info_tvCarb)
        tvTotalCalories = view.findViewById(R.id.frg_meal_info_tvTotalCalories)
        ilMealName = view.findViewById(R.id.frg_meal_designer_ilMealName)
        ilMealCategory = view.findViewById(R.id.frg_meal_designer_ilMealCategory)
        etMealName = view.findViewById(R.id.frg_meal_designer_etMealName)
        actvMealCategory = view.findViewById(R.id.frg_meal_designer_actvMealCategory)
        tvFiber = view.findViewById<TextView>(R.id.frg_meal_info_tvFiber)
        btnAddFood = view.findViewById(R.id.frg_meal_designer_btnAddFood)
        btnSaveMeal = view.findViewById(R.id.frg_meal_designer_btnSave)
        rvFood = view.findViewById(R.id.frg_meal_designer_rvFood)
        btnAddFood.setOnClickListener {
            onAddFood()
        }

        rvFood.layoutManager = LinearLayoutManager(view.context)
        val adapter = __FoodAdapterForLibrary(
            this.currentMeal.getList().toMutableList(),
            R.layout.entity_food_layout
        )
        adapter.setListener(this)
        rvFood.adapter = adapter
        val cl = view.findViewById<ConstraintLayout>(R.id.frg_meal_designer_cl)
        val swicth = view.findViewById<SwitchCompat>(R.id.frg_meal_info_swShow)
        swicth.setOnClickListener {
            if (swicth.isChecked) {
                cl.visibility = VISIBLE
            } else {
                cl.visibility = GONE
            }

        }

        btnSaveMeal.setOnClickListener {
            currentMeal.setTitle(etMealName.text.toString())
            currentMeal.category = actvMealCategory.text.toString()
            listener.detachFragmentMealInfo(this)
            listener.getCurrentMeal(currentMeal)
        }

        val icon = view.findViewById<ImageView>(R.id.frg_meal_info_ivCategory)
        setIcon(icon,view)
    }
    fun onAddFood() {
        listener.onAddFoodToMeal(this,dayIndex,currentMeal.getTitle())
    }
    fun setTexts(){
          etMealName.setText(currentMeal.getTitle())
        actvMealCategory.setText(currentMeal.category)
        val arrayAdaper=ArrayAdapter(requireContext(),R.layout.dropdown_generic_layout,resources.getStringArray(R.array.mealCategory))
        actvMealCategory.setAdapter(arrayAdaper)
        val totalCalories=__Utility.calculateTotalCalories(currentMeal)
        val totalProtein=__Utility.getTotalProteinGrams(currentMeal)*0.01f
        val totalFat=__Utility.getTotalFatGrams(currentMeal)*0.01f
        val totalCarbs=__Utility.getTotalCarbsGrams(currentMeal)*0.01f
        val totalFiber=__Utility.getTotalFiberGrams(currentMeal)*0.01f
        tvTotalCalories.text="${String.format("%.1f",totalCalories)} Kcal"
        tvProtein.text="${String.format("%.1f",totalProtein)} g protein"
        tvFat.text="${String.format("%.1f",totalFat)} g fat"
        tvCarb.text="${String.format("%.1f",totalCarbs)}g carbs"
        tvFiber.text="${String.format("%.1f",totalFiber)}g fiber"
        pcGraph.setEntries(arrayListOf(Pair("Protein",totalProtein*4f),Pair("Fat",totalFat*9f),Pair("Carbs",totalCarbs*4f)))
        val category=resources.getStringArray(R.array.mealCategory)
        val categoryAdapter=ArrayAdapter(requireContext(),R.layout.dropdown_generic_layout,category)
        actvMealCategory.setAdapter(categoryAdapter)
        actvMealCategory.setText(currentMeal.category)
        etMealName.setText(currentMeal.getTitle())


    }

    override fun onFoodItemClicked(currentFood: __Food) {
        val dialog=Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_modify_food_layout)
        dialog.show()
        val etQuantity=dialog.findViewById<EditText>(R.id.dialog_modify_etQuantity)
        val btnSave2=dialog.findViewById<Button>(R.id.dialog_modify_btnSave)
        val btnCancel=dialog.findViewById<Button>(R.id.dialog_modify_btnCancel)
        val actvServing=dialog.findViewById<AutoCompleteTextView>(R.id.dialog_modify_actvServing)
        val servingNameList = mutableListOf<String>()
        for (servings in currentFood.servingList) {
            servingNameList.add(servings.Name)
        }
        val servingAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_generic_layout, servingNameList)
        actvServing.setAdapter(servingAdapter)
        actvServing.setText(servingNameList.first())
        etQuantity.setText(String.format("%.1f",currentFood.Quantity))
        etQuantity.doOnTextChanged { text, start, before, count -> if(text.toString().isNotEmpty()){
        btnSave2.visibility= VISIBLE
        }
            else{
                btnSave2.visibility= INVISIBLE

        }        }
        btnSave2.setOnClickListener {
            val cond=etQuantity.text.toString().isNotEmpty()
            val cond2=actvServing.text.toString().isNotEmpty()
            if(cond and cond2){
                var serving=__Serving()
                for(servings in currentFood.servingList){
                    if(servings.Name==actvServing.text.toString()){
                        serving=servings
                    }
                }
                currentFood.Quantity=etQuantity.text.toString().toFloat()*serving.quantity
                currentMeal.updateFood(currentFood)
                refreshUI()
                listener.getCurrentMeal(currentMeal)
                dialog.dismiss()
            }
        }
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

    }
fun setIcon(imageView: ImageView,view: View){
    imageView.imageTintList=view.context.getColorStateList(R.color.colorMainMeal)
    if(currentMeal.category=="Breakfast"){
        imageView.setImageDrawable(requireContext().getDrawable( R.drawable.icon_breakfast))

    }
    if(currentMeal.category=="Lunch"){
        imageView.setImageDrawable(requireContext().getDrawable( R.drawable.icon_lunch))
    }
    if(currentMeal.category=="Dinner"){
        imageView.setImageDrawable(requireContext().getDrawable( R.drawable.icon_dinner))
    }
    if(currentMeal.category=="Snack"){
        imageView.setImageDrawable(requireContext().getDrawable( R.drawable.icon_snack))
        imageView.imageTintList=view.context.getColorStateList(R.color.colorSnack)
    }
    if(currentMeal.category=="Pre-Workout Meal"){
        imageView.setImageDrawable(requireContext().getDrawable( R.drawable.icon_workout))
        imageView.imageTintList=view.context.getColorStateList(R.color.colorWorkout)
    }
    if(currentMeal.category=="Post-Workout Meal"){
        imageView.setImageDrawable(requireContext().getDrawable( R.drawable.icon_workout))
        imageView.imageTintList=view.context.getColorStateList(R.color.colorWorkout)
    }
}
    private fun refreshUI() {
        val totalCalories=__Utility.calculateTotalCalories(currentMeal)
        val totalProtein=__Utility.getTotalProteinGrams(currentMeal)*0.01f
        val totalFat=__Utility.getTotalFatGrams(currentMeal)*0.01f
        val totalCarbs=__Utility.getTotalCarbsGrams(currentMeal)*0.01f
        val totalFiber=__Utility.getTotalFiberGrams(currentMeal)*0.01f
        tvTotalCalories.text="${String.format("%.1f",totalCalories)} Kcal"
        tvProtein.text="${String.format("%.1f",totalProtein)} g protein"
        tvFat.text="${String.format("%.1f",totalFat)} g fat"
        tvCarb.text="${String.format("%.1f",totalCarbs)}g carbs"
        tvFiber.text="${String.format("%.1f",totalFiber)}g fiber"
        pcGraph.setEntries(arrayListOf(Pair("Protein",totalProtein*4f),Pair("Fat",totalFat*9f),Pair("Carbs",totalCarbs*4f)))
    }

}