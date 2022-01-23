package com.example.macromanager.Fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.macromanager.Adapters.__MealAdapter
import com.example.macromanager.Entity.__Food

import com.example.macromanager.Entity.__Meal
import com.example.macromanager.Entity.__User3
import com.example.macromanager.Listeners.__AdapterMealListener
import com.example.macromanager.Listeners.FragmentListener.__FragmentHomeListener
import com.example.macromanager.R
import com.example.macromanager.ViewModel.__UserViewModel
import com.example.macromanager.ViewModelFactory.__UserViewModelFactory
import com.example.myapplication.Entity.__Date

class __FragmentHome(private val user: __User3, ):Fragment(),__AdapterMealListener {
     private lateinit var listener: __FragmentHomeListener
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
        // viewModel.getProperty().updateValues()
         recyclerView = view.findViewById<RecyclerView>(R.id.frg_home_rvMeal)
         rvAdapter = __MealAdapter(
            viewModel.getProperty().mealCurrentList, R.layout.entity_meal_layout
        )
        rvAdapter.setListToBeDiplayed(mutableListOf(__Meal("Breakfast").apply {
            this.addFood(__Food())

        },__Meal("Lunch")))
        rvAdapter.setListener(this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = rvAdapter
        initProgressBars(view)
        initTextViews(view)
        updateStats()
        return view


    }

    fun initProgressBars(view: View){
        pbCalories=view.findViewById(R.id.frg_home_pbCalories)
        pbProtein=view.findViewById(R.id.frg_home_pbProtein)
        pbCarbs=view.findViewById(R.id.frg_home_pbCarb)
        pbFat=view.findViewById(R.id.frg_home_pbFat)
    }
    fun initTextViews(view: View){
        tvCalories=view.findViewById(R.id.frg_home_tvCurrent)
        tvProtein=view.findViewById(R.id.frg_home_tvProtein)
        tvCarbs=view.findViewById(R.id.frg_home_tvCarb)
        tvFat=view.findViewById(R.id.frg_home_tvFat)
        tvDay=view.findViewById(R.id.frg_home_tvDay)
        tvTotal=view.findViewById(R.id.frg_home_tvDailyIntake)
        tvCardio=view.findViewById(R.id.frg_home_tvCardio)
        val dayString= __Date()
        tvCardio.text="${viewModel.getProperty().userDietPlan!!.cardio} test"
        tvTotal.text="${viewModel.getProperty().GetCalories()} test"
        tvDay.text=dayString.getDateAsString()
    }
    fun updateStats(){
        updateValues()
        refreshDashboard()

    }

    private fun refreshDashboard() {
        val info=viewModel.getProperty()

        val caloriesProgress=totalCalories.toFloat()/(info.GetCalories())*100
        val proteinProgress=totalProtein/(info.GetCalories()*info.userDietPlan!!.proteinPercent!!*(1/4f))*100
        val fatProgress=totalFat/(info.GetCalories()*info.userDietPlan!!.fatPercent!!*(1/9f))*100
        val carbProgress=totalCarbs/(info.GetCalories()*info.userDietPlan!!.carbPercent!!*(1/4f))*100
        val calorie_string="${(info.GetCalories()-totalCalories)} kCal"
        val protein_string="${totalProtein}/${(info.GetCalories()*info.userDietPlan!!.proteinPercent!!*(1/4f)).toInt()}g of proteins"
        val fat_string="${totalFat}/${(info.GetCalories()*info.userDietPlan!!.fatPercent!!*(1/9f)).toInt()}g of fats"
        val carb_string="${totalCarbs}/${(info.GetCalories()*info.userDietPlan!!.carbPercent!!*(1/4f)).toInt()}g of cabs"

        tvCalories.text=calorie_string
        tvProtein.text=protein_string
        tvCarbs.text=carb_string
        tvTotal.text="${viewModel.getProperty().GetCalories()} kcal"
        pbCalories.setProgress(caloriesProgress.toInt(),true)
        pbProtein.setProgress(proteinProgress.toInt(),true)
        pbFat.setProgress(fatProgress.toInt(),true)
        pbCarbs.setProgress(carbProgress.toInt(),true)

        if(info.GetCalories()-totalCalories<0){
            tvCalories.setTextColor(Color.parseColor("#D84315"))
        }
        else{
            tvCalories.setTextColor(Color.parseColor("#558B2F"))
        }
        tvFat.text=fat_string
        tvCardio.text="${viewModel.getProperty().userDietPlan!!.cardio} kcal"
    }
    fun updateValues(){
        totalCarbs=0
        totalFat=0
        totalProtein=0
        totalCalories=0
        for(meal in viewModel.getProperty().mealCurrentList!!){
            for(food in meal.getList()){
                totalCalories+=(food.Quantity*food.caloriesIn100UM/100).toInt()
                totalProtein+=(food.Quantity*food.nProteins/100).toInt()
                totalFat+=(food.Quantity*food.nFats/100).toInt()
                totalCarbs+=(food.Quantity*food.nCarbs/100).toInt()
            }
        }
    }

    override fun onMealClicked(currentMeal: __Meal) {
    listener.attachFragmentMealInfo(currentMeal)
    }

    fun setListener(newListener: __FragmentHomeListener) {
        this.listener=newListener

    }
}

