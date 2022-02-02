package com.example.macromanager.Fragments

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.macromanager.Adapters.__MealAdapter
import com.example.macromanager.Entity.__Day
import com.example.macromanager.Entity.__Food

import com.example.macromanager.Entity.__Meal
import com.example.macromanager.Entity.__User3
import com.example.macromanager.Listeners.__AdapterMealListener
import com.example.macromanager.Listeners.FragmentListener.__FragmentHomeListener
import com.example.macromanager.Listeners.FragmentListener.__FragmentMealInfoListener
import com.example.macromanager.Listeners.FragmentListener.__HomeLibraryComm
import com.example.macromanager.R
import com.example.macromanager.ViewModel.__UserViewModel
import com.example.macromanager.ViewModelFactory.__UserViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout

class __FragmentMainHome(private val user: __User3, ):Fragment(),__AdapterMealListener,__FragmentMealInfoListener {
     private lateinit var listener: __FragmentHomeListener
     private lateinit var comm:__HomeLibraryComm
     fun setComm(newComm: __HomeLibraryComm){
         this.comm=newComm
     }
    private lateinit var  fabAdd: FloatingActionButton
    private lateinit var fabMeal: FloatingActionButton
    private lateinit var fabCardio: FloatingActionButton
    private lateinit var viewModel: __UserViewModel
    private lateinit var recyclerView:RecyclerView
    private lateinit var rvAdapter:__MealAdapter
    private lateinit var pbCalories: ProgressBar
    private lateinit var pbFat:ProgressBar
    private lateinit var pbProtein:ProgressBar
    private lateinit var pbCarbs:ProgressBar
    private lateinit var tvCalories: TextView
    private lateinit var tvProtein:TextView
    private lateinit var tvFat:TextView
    private lateinit var tvCarbs:TextView
    private lateinit var tvDay: TextView
    private lateinit var tvTotal: TextView
    private lateinit var tvCardio: TextView
    private  lateinit var rotateOpen: Animation
    private  lateinit var rotateClose: Animation
    private  lateinit var toBottom: Animation
    private  lateinit var fromBottom: Animation
     private lateinit var btnDayBefore:ImageButton
    private lateinit var btnDayAfter:ImageButton
    private var dayIndex=user.dayHistory.size-1
    private var clicked=false
    private var totalCalories:Int=0
    private var totalProtein=0
    private var totalFat=0
    private var totalCarbs=0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_home, container, false)

        viewModel = ViewModelProvider(this, __UserViewModelFactory(user))[__UserViewModel::class.java]

         recyclerView = view.findViewById<RecyclerView>(R.id.frg_home_rvMeal)
        rotateOpen= AnimationUtils.loadAnimation(requireContext(),R.anim.rotate_open_anim)
        rotateClose= AnimationUtils.loadAnimation(requireContext(),R.anim.rotate_close_anim)
        fromBottom= AnimationUtils.loadAnimation(requireContext(),R.anim.from_bottom_anim)
        toBottom= AnimationUtils.loadAnimation(requireContext(),R.anim.to_bottom_anim)
         rvAdapter = __MealAdapter(
            viewModel.getProperty().dayHistory.last().mealList, R.layout.entity_meal_layout
        )

        rvAdapter.setListener(this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = rvAdapter
        initProgressBars(view)
        initTextViews(view)
        initFabs(view)
        refreshDashboard(viewModel.getProperty().dayHistory[dayIndex])
        comm.getDaySelectedInfo(dayIndex,viewModel.getProperty().dayHistory[dayIndex].createMealNameList())
        return view


    }

    override fun onPause() {
        super.onPause()
        listener.updateCurrentUser(viewModel.getProperty())
    }
    fun initProgressBars(view: View){
        pbCalories=view.findViewById(R.id.frg_home_pbCalories)
        pbProtein=view.findViewById(R.id.frg_home_pbProtein)
        pbCarbs=view.findViewById(R.id.frg_home_pbCarb)
        pbFat=view.findViewById(R.id.frg_home_pbFat)
    }
    fun initFabs(view: View){
        fabAdd=view.findViewById(R.id.frg_main_fabAdd)
        fabCardio=view.findViewById(R.id.frg_home_fabCardio)
        fabMeal=view.findViewById(R.id.frg_home_fabMeal)
        fabAdd.setOnClickListener {
            onAddButtonClicked()
        }
        fabMeal.setOnClickListener {
            onMealAdded()
        }
        fabCardio.setOnClickListener {
            onSetCardio()

        }
    }

    private fun onSetCardio() {

    }

    private fun onMealAdded() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_add_meal_layout)
        dialog.show()
        val etMealName = dialog.findViewById<EditText>(R.id.dialog_add_meal_etMealName)
        val btnSave = dialog.findViewById<Button>(R.id.dialog_add_meal_btnSave)
        val btnCancel = dialog.findViewById<Button>(R.id.dialog_add_meal_btnCancel)
        val ilMealName = dialog.findViewById<TextInputLayout>(R.id.dialog_add_meal_ilMealName)
        val ilCatr=dialog.findViewById<TextInputLayout>(R.id.dialog_add_meal_ilMealCategory)
        val actvCategory =
            dialog.findViewById<AutoCompleteTextView>(R.id.dialog_add_meal_actvCategory)
        val Category = resources.getStringArray(R.array.mealCategory)

        val categoryAdapter =
            ArrayAdapter(requireContext(), R.layout.dropdown_generic_layout, Category)
        actvCategory.setAdapter(categoryAdapter)

        etMealName.doOnTextChanged { text, start, before, count ->
            if (text.toString().isNotEmpty()) {
                btnSave.visibility = View.VISIBLE
            } else {
                btnSave.visibility = View.INVISIBLE

            }
        }
        btnSave.setOnClickListener {
            val cond = onUserInteraction(ilMealName,etMealName)
            val cond2 =onUserInteraction(ilCatr,actvCategory)
            if (cond and cond2) {
                val meal = __Meal(etMealName.text.toString())
                meal.category = actvCategory.text.toString()
               // viewModel.getProperty().addMeal(meal)
              //  rvAdapter.addItem(meal)
                viewModel.getProperty().dayHistory[dayIndex].mealList.add(meal)
                Log.d("Test","onAddFood viewModel ItemCount ${viewModel.getProperty().dayHistory[dayIndex].mealList.size}")
                // rvAdapter.setListToBeDisplayed(viewModel.getProperty().dayHistory[dayIndex].mealList)
               // comm.getDaySelectedInfo(dayIndex,viewModel.getProperty().dayHistory[dayIndex].createMealNameList())
                comm.onAddMeal(dayIndex,meal.getTitle())

            }
            dialog.dismiss()
        }
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
    }



    private fun onAddButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        isClickable(clicked)
        clicked=!clicked
    }

    private fun setVisibility(switch: Boolean) {
        if(!switch){
            fabMeal.visibility= View.INVISIBLE
            fabCardio.visibility= View.INVISIBLE
        }else{

            fabMeal.visibility= View.VISIBLE
            fabCardio.visibility= View.VISIBLE

        }
    }

    private fun setAnimation(switch: Boolean) {
        if(!switch){
            fabMeal.startAnimation(fromBottom)
            fabCardio.startAnimation(fromBottom)
            fabAdd.startAnimation(rotateOpen)
        }else{

            fabMeal.startAnimation(toBottom)
            fabCardio.startAnimation(toBottom)
            fabAdd.startAnimation(rotateClose)

        }
    }
    private fun isClickable(switch:Boolean) {

        if (!switch) {
            fabMeal.isClickable = true
            fabCardio.isClickable = true
        } else {

            fabMeal.isClickable = false
            fabCardio.isClickable = false

        }
    }
    fun initTextViews(view: View){
        tvCalories=view.findViewById(R.id.frg_home_tvCurrent)
        tvProtein=view.findViewById(R.id.frg_home_tvProtein)
        tvCarbs=view.findViewById(R.id.frg_home_tvCarb)
        tvFat=view.findViewById(R.id.frg_home_tvFat)
        tvDay=view.findViewById(R.id.frg_home_tvCurrentDay)
        tvTotal=view.findViewById(R.id.frg_home_tvDailyIntake)
        tvCardio=view.findViewById(R.id.frg_home_tvCardio)

        tvCardio.text="${viewModel.getProperty().userDietPlan!!.cardio} test"
        tvTotal.text="${viewModel.getProperty().GetCalories()} test"
        tvDay.text=user.retrieveDay(user.dayHistory.size-1).dateOfDay.getDateAsString()
        btnDayAfter=view.findViewById(R.id.frg_home_btnDayAfter)
        btnDayBefore=view.findViewById(R.id.frg_home_btnDayBefore)
        btnDayBefore.setOnClickListener {
            dayIndex--
        if(dayIndex<0){
            dayIndex=viewModel.getProperty().dayHistory.size-1

        }
            Log.d("Test","mealSize:${viewModel.getProperty().dayHistory[dayIndex].mealList.size}")
           onDayChanged(dayIndex)
        }
        btnDayAfter.setOnClickListener {
            dayIndex++
            if(dayIndex>viewModel.getProperty().dayHistory.size-1){
                dayIndex=0
            }
            Log.d("Test","mealSize:${viewModel.getProperty().dayHistory[dayIndex].mealList.size}")
            onDayChanged(dayIndex)
        }
    }

    fun updateValues(day:__Day){
        totalCarbs=0
        totalFat=0
        totalProtein=0
        totalCalories=0
        for(meal in day.mealList){
            for(food in meal.getList()){
                totalCalories+=(food.Quantity*food.caloriesIn100UM/100).toInt()
                totalProtein+=(food.Quantity*food.nProteins/100).toInt()
                totalFat+=(food.Quantity*food.nFats/100).toInt()
                totalCarbs+=(food.Quantity*food.nCarbs/100).toInt()
            }
        }
    }

    override fun onMealClicked(currentMeal: __Meal) {
    listener.attachFragmentMealInfo(currentMeal,dayIndex)

    }

    override fun clearCurrentMeal(meal: __Meal) {
       //viewModel.getProperty().updateMeal(meal)
        rvAdapter.removeMeal(meal)
        viewModel.getProperty().removeMeal(dayIndex,meal)
        refreshDashboard(viewModel.getProperty().dayHistory[dayIndex])
        comm.getDaySelectedInfo(dayIndex,viewModel.getProperty().dayHistory[dayIndex].createMealNameList())
    }

    fun setListener(newListener: __FragmentHomeListener) {
        this.listener=newListener

    }

    override fun detachFragmentMealInfo(fragment: __FragmentMealInfo) {
        listener.detachFragmentMealInfo(fragment)
    }



    override fun onAddFoodToMeal(fragment: __FragmentMealInfo,dayIndex: Int,mealName: String) {
        listener.onAddFoodToMeal(fragment,dayIndex,mealName)
    }

    override fun attachFragmentFoodInfo(currentFood: __Food, currentMeal: String) {

        listener.attachFragmentFoodInfo(currentFood,currentMeal,dayIndex)

    }

    override fun getCurrentMeal(currentMeal: __Meal) {
        viewModel.getProperty().updateMeal(dayIndex,currentMeal)

        refreshDashboard(viewModel.getProperty().dayHistory[dayIndex])
    }

    fun onAddFoodToMeal(currentFood: __Food, mealName: String,dayIndex: Int) {
        viewModel.getProperty().addFoodToMeal(dayIndex,currentFood,mealName)
        refreshDashboard(viewModel.getProperty().dayHistory[dayIndex])
    }
    fun onUserInteraction(inputLayout: TextInputLayout,inputEditText: EditText):Boolean{
        if(inputEditText.text!!.isEmpty()){
            inputLayout.setHelperTextColor(resources.getColorStateList(R.color.red))
        }else{
            inputLayout.setHelperTextColor(resources.getColorStateList(R.color.colorHint))
        }
        return inputEditText.text!!.isNotEmpty()
    }
    fun refreshDashboard(day:__Day){
        updateValues(day)
        updateViews(day)
    }

    private fun updateViews(day: __Day) {
        val totalCaloriesfromUser=viewModel.getProperty().userDietPlan.calculateTotalCalories()
        val caloriesProgress=totalCalories.toFloat()/(totalCaloriesfromUser)*100
        val proteinProgress=totalProtein/(totalCaloriesfromUser*user.userDietPlan.proteinPercent*(1/4f))*100
        val fatProgress=totalFat/(totalCaloriesfromUser*user.userDietPlan.fatPercent*(1/9f))*100
        val carbProgress=totalCarbs/(totalCaloriesfromUser*user.userDietPlan.carbPercent*(1/4f))*100
        val calorie_string="${(totalCaloriesfromUser-totalCalories)} kCal"
        val protein_string="${totalProtein}/${(totalCaloriesfromUser*user.userDietPlan.proteinPercent*(1/4f)).toInt()}g of proteins"
        val fat_string="${totalFat}/${(totalCaloriesfromUser*user.userDietPlan.fatPercent*(1/9f)).toInt()}g of fats"
        val carb_string="${totalCarbs}/${(totalCaloriesfromUser*user.userDietPlan.carbPercent*(1/4f)).toInt()}g of cabs"

        tvCalories.text=calorie_string
        tvProtein.text=protein_string
        tvCarbs.text=carb_string
        tvTotal.text="${viewModel.getProperty().GetCalories()} kcal"
        pbCalories.setProgress(caloriesProgress.toInt(),true)
        pbProtein.setProgress(proteinProgress.toInt(),true)
        pbFat.setProgress(fatProgress.toInt(),true)
        pbCarbs.setProgress(carbProgress.toInt(),true)
        if(user.GetCalories()-totalCalories<0){
            tvCalories.setTextColor(Color.parseColor("#D84315"))
        }
        else{
            tvCalories.setTextColor(Color.parseColor("#558B2F"))
        }
        tvFat.text=fat_string
        tvCardio.text="${viewModel.getProperty().userDietPlan!!.cardio} kcal"
    }
    fun onDayChanged(dayIndex:Int){
        Log.d("Test"," ///////////////////////////////////////////////")
        refreshDashboard(viewModel.getProperty().dayHistory[dayIndex])


        Log.d("Test"," Adapter Before ItemCount:${rvAdapter.itemCount}")
        Log.d("Test","ViewModel Before ItemCount:${viewModel.getProperty().dayHistory[dayIndex].mealList.size}")
        rvAdapter.setListToBeDisplayed(viewModel.getProperty().dayHistory[dayIndex].mealList)
        Log.d("Test","Aapter After ItemCount:${rvAdapter.itemCount}")
        Log.d("Test","ViewModel After ItemCount:${viewModel.getProperty().dayHistory[dayIndex].mealList.size}")
        Log.d("Test"," ///////////////////////////////////////////////")
        tvDay.setText(viewModel.getProperty().dayHistory[dayIndex].dateOfDay.getDateAsString())
        comm.getDaySelectedInfo(dayIndex,viewModel.getProperty().dayHistory[dayIndex].createMealNameList())
    }

    fun updateUser(user3: __User3) {
        viewModel.setProperty(user3)
    }

}

