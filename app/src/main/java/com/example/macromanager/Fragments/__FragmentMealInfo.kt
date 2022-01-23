package com.example.macromanager.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.addCallback
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.macromanager.Entity.__Meal
import com.example.macromanager.Listeners.FragmentListener.__FragmentMealInfoListener
import com.example.macromanager.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class __FragmentMealInfo(val currentMeal: __Meal) : Fragment() {
private lateinit var listener: __FragmentMealInfoListener
fun setListener(newListener: __FragmentMealInfoListener){
    this.listener=newListener
}
    private lateinit var pbProtein:ProgressBar
    private lateinit var pbFat:ProgressBar
    private lateinit var pbCarb:ProgressBar
    private lateinit var tvProtein:TextView
    private lateinit var tvFat:TextView
    private lateinit var tvCarb:TextView
    private lateinit var tvTotalCalories:TextView
    private lateinit var ilMealName:TextInputLayout
    private lateinit var ilMealCategory:TextInputLayout
    private lateinit var etMealName:TextInputEditText
    private lateinit var actvMealCategory:AutoCompleteTextView
    private lateinit var macroConstraintLayout:ConstraintLayout
    private lateinit var swicthMacro:SwitchCompat
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
    fun initViews(view:View){
        pbProtein=view.findViewById(R.id.frg_meal_designer_pbProtein)
        pbFat=view.findViewById(R.id.frg_meal_designer_pbFat)
        pbCarb=view.findViewById(R.id.frg_meal_designer_pbCarbs)
        tvProtein=view.findViewById(R.id.frg_meal_designer_tvProtein)
        tvFat=view.findViewById(R.id.frg_meal_designer_tvFat)
        tvCarb=view.findViewById(R.id.frg_meal_designer_tvCarb)
        tvTotalCalories=view.findViewById(R.id.frg_meal_designer_tvTotalCalories)
        ilMealName=view.findViewById(R.id.frg_meal_designer_ilMealName)
        ilMealCategory=view.findViewById(R.id.frg_meal_designer_ilMealCategory)
        etMealName=view.findViewById(R.id.frg_meal_designer_etMealName)
        actvMealCategory=view.findViewById(R.id.frg_meal_designer_actvMealCategory)

        btnAddFood=view.findViewById(R.id.frg_meal_designer_btnAddFood)
        btnSaveMeal=view.findViewById(R.id.frg_meal_designer_btnSave)
        rvFood=view.findViewById(R.id.frg_meal_designer_rvFood)
        btnAddFood.setOnClickListener {
            onAddFood()
        }
    }
    fun onAddFood() {
        listener.onAddFood(this)
    }


}