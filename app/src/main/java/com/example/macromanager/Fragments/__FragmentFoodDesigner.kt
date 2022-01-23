package com.example.macromanager.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.example.macromanager.Adapters.__Serving
import com.example.macromanager.Entity.__Food
import com.example.macromanager.Listeners.FragmentListener.__FragmentFoodDesignerListener
import com.example.macromanager.R
import com.example.myapplication.Listeners.__FoodRepositoryListener
import com.example.myapplication.Repository.__FoodRepository2
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class __FragmentFoodDesigner : Fragment(),__FoodRepositoryListener {
    private lateinit var listener: __FragmentFoodDesignerListener
    private lateinit var ilFoodName:TextInputLayout
    private lateinit var ilBrand:TextInputLayout
    private lateinit var ilServingName:TextInputLayout
    private lateinit var ilServingQuantity:TextInputLayout
    private lateinit var ilUnitOfMeasure:TextInputLayout
    private lateinit var ilCategory:TextInputLayout
    private lateinit var ilProtein:TextInputLayout
    private lateinit var ilFat:TextInputLayout
    private lateinit var ilCarb:TextInputLayout
    private lateinit var ilFiber:TextInputLayout
    private lateinit var etFoodName:TextInputEditText
    private lateinit var etBrand:TextInputEditText
    private lateinit var etServingName:TextInputEditText
    private lateinit var etServingQuantity:TextInputEditText
    private lateinit var actvUnitOfMeasure:AutoCompleteTextView
    private lateinit var actvCategory:AutoCompleteTextView
    private lateinit var etProtein:TextInputEditText
    private lateinit var etFat:TextInputEditText
    private lateinit var etCarb:TextInputEditText
    private lateinit var etFiber:TextInputEditText




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        __FoodRepository2.setListener(this)
        val view = inflater.inflate(R.layout.fragment_food_designer, container, false)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this.viewLifecycleOwner) {
            listener.detachFragmentFoodDesigner(this@__FragmentFoodDesigner)
        }

        val umString = resources.getStringArray(R.array.unitOfMeasureName)
        val categoryString = resources.getStringArray(R.array.foodCategory)
        val umAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_generic_layout, umString)
        val categoryAdapter =
            ArrayAdapter(requireContext(), R.layout.dropdown_generic_layout, categoryString)
        actvCategory = view.findViewById(R.id.frg_food_designer_actvCategory)
        actvUnitOfMeasure = view.findViewById(R.id.frg_food_designer_actvUnitOfMeasure)
        actvCategory.setAdapter(categoryAdapter)
        actvUnitOfMeasure.setAdapter(umAdapter)
        ilFoodName = view.findViewById(R.id.frg_food_designer_ilFoodName)
        ilBrand = view.findViewById(R.id.frg_food_designer_ilBrand)
        ilServingName = view.findViewById(R.id.frg_food_designer_ilServingName)
        ilServingQuantity = view.findViewById(R.id.frg_food_designer_ilServingQuantity)
        ilUnitOfMeasure = view.findViewById(R.id.frg_food_designer_ilUnitOfMeasure)
        ilCategory = view.findViewById(R.id.frg_food_designer_ilCategory)
        ilProtein = view.findViewById(R.id.frg_food_designer_ilProtein)
        ilFat = view.findViewById(R.id.frg_food_designer_ilFat)
        ilCarb = view.findViewById(R.id.frg_food_designer_ilCarb)
        ilFiber = view.findViewById(R.id.frg_food_designer_ilFiber)
        etFoodName = view.findViewById(R.id.frg_food_designer_etFoodName)
        etBrand = view.findViewById(R.id.frg_food_designer_etBrand)
        etServingName = view.findViewById(R.id.frg_food_designer_etServingName)
        etServingQuantity = view.findViewById(R.id.frg_food_designer_etServingQuantity)
        etProtein = view.findViewById(R.id.frg_food_designer_etProtein)
        etFat = view.findViewById(R.id.frg_food_designer_etFat)
        etCarb = view.findViewById(R.id.frg_food_designer_etCarb)
        etFiber = view.findViewById(R.id.frg_food_designer_etFiber)
        val btnAdd = view.findViewById<Button>(R.id.frg_food_designer_btnAdd)
        btnAdd.setOnClickListener {
            val booleanList = mutableListOf(
                onUserInteraction(ilFoodName, etFoodName),
                onUserInteraction(ilBrand, etBrand),
                onUserInteraction(ilUnitOfMeasure, actvUnitOfMeasure),
                onUserInteraction(ilCategory, actvCategory),
                onUserInteraction(ilProtein, etProtein),
                onUserInteraction(ilFat, etFat),
                onUserInteraction(ilCarb, etCarb)
            )
            var ok = true;
            for (boolean in booleanList) {
                ok = boolean
            }
            if (ok) {
                val foodName = etFoodName.text.toString()
                val foodBrand = etBrand.text.toString()
                var serving: __Serving?
                if (etServingName.text!!.isNotEmpty() and etServingQuantity.text.toString()
                        .isNotEmpty()
                ) {
                    serving = __Serving(
                        etServingName.text.toString(),
                        actvUnitOfMeasure.text.toString(),
                        etServingQuantity.text.toString().toDouble()
                    )
                } else {
                    serving = __Serving()
                }
                val unitOfMeasure = actvUnitOfMeasure.text.toString()
                val category = actvCategory.text.toString()
                val protein = etProtein.text.toString().toDouble()
                val carb = etCarb.text.toString().toDouble()
                val fat = etFat.text.toString().toDouble()
                var fiber: Double = 0.0
                if (etFiber.text.toString().isNotEmpty()) {
                    fiber = etFiber.text.toString().toDouble()
                }
                val food = __Food(
                    foodName,
                    unitOfMeasure,
                    arrayListOf(serving),
                    foodBrand,
                    category,
                    100.0,
                    protein * 4.0 + fat * 8.8 + carb * 4.0,
                    protein,
                    fat,
                    carb,
                    fiber
                )

                __FoodRepository2.getInstance()?.createFood(food)
                listener.detachFragmentFoodDesigner(this)
            }

        }
        return view
    }

    override fun onResume() {
        super.onResume()

        val umString=resources.getStringArray(R.array.unitOfMeasureName)
        val categoryString=resources.getStringArray(R.array.foodCategory)
        val umAdapter=ArrayAdapter(requireContext(),R.layout.dropdown_generic_layout,umString)
        val categoryAdapter=ArrayAdapter(requireContext(),R.layout.dropdown_generic_layout,categoryString)
        actvCategory?.setAdapter(categoryAdapter)
        actvUnitOfMeasure?.setAdapter(umAdapter)
    }
    fun onUserInteraction(inputLayout: TextInputLayout,inputEditText: EditText):Boolean{
       if(inputEditText.text!!.isEmpty()){
           inputLayout.setHelperTextColor(resources.getColorStateList(R.color.red))
       }else{
           inputLayout.setHelperTextColor(resources.getColorStateList(R.color.colorHint))
       }
        return inputEditText.text!!.isNotEmpty()
    }

    override fun getRequestStatus(requestName: String, status: Boolean, error: String?) {
        if(requestName=="CREATE_FOOD"){
            if(status){
                CoroutineScope(Dispatchers.Main).launch {
                    //Toast.makeText(requireContext(), "Food Created!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        if(error!=null){
            CoroutineScope(Dispatchers.Main).launch {
               // Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun getFoodQuery(query: MutableList<__Food>) {

    }

    fun setListener(newListener: __FragmentFoodDesignerListener) {
        this.listener=newListener
    }

}