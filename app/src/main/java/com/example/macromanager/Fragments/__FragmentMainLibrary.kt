package com.example.macromanager.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.macromanager.Adapters.__FoodAdapterForLibrary
import com.example.macromanager.Entity.__Food
import com.example.macromanager.Entity.__User3
import com.example.macromanager.Listeners.FragmentListener.__FragmentFoodDesignerListener
import com.example.macromanager.Listeners.FragmentListener.__FragmentFoodInfoListener
import com.example.macromanager.Listeners.__AdapterFoodListener
import com.example.macromanager.Listeners.FragmentListener.__FragmentFoodLibraryListener
import com.example.macromanager.Listeners.FragmentListener.__HomeLibraryComm
import com.example.macromanager.R
import com.example.macromanager.ViewModel.__UserViewModel
import com.example.macromanager.ViewModelFactory.__UserViewModelFactory
import com.example.myapplication.Listeners.__FoodRepositoryListener
import com.example.myapplication.Repository.__FoodRepository2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class __FragmentMainLibrary(val currentUser:__User3):Fragment(R.layout.fragment_main_food_library),__FoodRepositoryListener,__AdapterFoodListener,__FragmentFoodInfoListener,__FragmentFoodDesignerListener,__HomeLibraryComm {
    private var currentDayIndex:Int=currentUser.dayHistory.size-1
    private var mealList: MutableList<String> = currentUser.retrieveDay(currentDayIndex).createMealNameList()
    private lateinit var listener: __FragmentFoodLibraryListener
    private lateinit var ilSearch:TextInputLayout
    private lateinit var etSearch:TextInputEditText
    private lateinit var pbSearch:ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter:__FoodAdapterForLibrary
    private lateinit var viewModel:__UserViewModel
    private  lateinit var rotateOpen:Animation
    private  lateinit var rotateClose:Animation
    private  lateinit var toBottom:Animation
    private  lateinit var fromBottom:Animation
    private var clicked=false
    private lateinit var  fabAdd:FloatingActionButton
    private lateinit var fabFood:FloatingActionButton
    private lateinit var fabMeal:FloatingActionButton
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_food_library, container, false)
        __FoodRepository2.setListener(this)
        rotateOpen=AnimationUtils.loadAnimation(requireContext(),R.anim.rotate_open_anim)
        rotateClose=AnimationUtils.loadAnimation(requireContext(),R.anim.rotate_close_anim)
        fromBottom=AnimationUtils.loadAnimation(requireContext(),R.anim.from_bottom_anim)
        toBottom=AnimationUtils.loadAnimation(requireContext(),R.anim.to_bottom_anim)
        ilSearch = view.findViewById(R.id.frg_library_ilSearch)
        etSearch = view.findViewById(R.id.frg_library_etSearch)
        recyclerView = view.findViewById(R.id.frg_library_rvfood)
        fabAdd=view.findViewById(R.id.frg_library_fabAdd)
        fabFood=view.findViewById(R.id.frg_library_fabFood)
        fabMeal=view.findViewById(R.id.frg_library_fabMeal)
        viewModel = ViewModelProvider(
            this@__FragmentMainLibrary,
            __UserViewModelFactory(currentUser)
        )[__UserViewModel::class.java]
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = __FoodAdapterForLibrary(mutableListOf(), R.layout.entity_food_layout)
        adapter.setListener(this)
        recyclerView.adapter = adapter
        fabAdd.setOnClickListener {
onAddButtonClicked()
        }
        fabMeal.setOnClickListener {
            onMealCreated()
        }
        fabFood.setOnClickListener {
            onFoodCreated()

        }

        pbSearch = view.findViewById(R.id.frg_library_pbSearch)
        etSearch.doOnTextChanged { text, start, before, count ->
            if (etSearch.text.toString().isNotEmpty()) {
                pbSearch.visibility = VISIBLE
                __FoodRepository2.getInstance()?.queryFood(etSearch.text.toString())
            }
        }


        return view
    }

    private fun onFoodCreated() {
        //listener.navigateToFoodDesigner()
        listener.attachFragmentFoodDesigner()
    }

    private fun onMealCreated() {

    }

    private fun onAddButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        isClickable(clicked)
        clicked=!clicked
    }

    private fun setVisibility(switch: Boolean) {
     if(!switch){
         fabMeal.visibility= INVISIBLE
         fabFood.visibility= INVISIBLE
     }else{

             fabMeal.visibility= VISIBLE
             fabFood.visibility= VISIBLE

     }
    }

    private fun setAnimation(switch: Boolean) {
        if(!switch){
            fabMeal.startAnimation(fromBottom)
            fabFood.startAnimation(fromBottom)
            fabAdd.startAnimation(rotateOpen)
        }else{

            fabMeal.startAnimation(toBottom)
            fabFood.startAnimation(toBottom)
            fabAdd.startAnimation(rotateClose)

        }
    }
    private fun isClickable(switch:Boolean){

        if(!switch){
            fabMeal.isClickable= true
            fabFood.isClickable= true
        }else{

            fabMeal.isClickable= false
            fabFood.isClickable= false

        }

    }

    override fun getRequestStatus(requestName: String, status: Boolean, error: String?) {
        if (requestName == "QUERY_FOOD") {
            if (status) {
                pbSearch.visibility = INVISIBLE
            }
        }
        if (requestName == "RETRIEVE_USER") {
            if (status) {

            }
        }

        error?.let {
            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getFoodQuery(query: MutableList<__Food>) {
        adapter.setListToBeDisplayed(query)
    }

    fun setListener(newListener: __FragmentFoodLibraryListener) {
        this.listener=newListener

    }
//TODO(de gandit la o varianta astfel incat foodLibrary sa stie ce zii este selectata)
    override fun onFoodItemClicked(currentFood: __Food) {
        listener.attachFragmentFoodInfo(currentFood,currentDayIndex,mealList)
    }

    override fun attachFragmentFoodDesigner() {
        listener.attachFragmentFoodDesigner()
    }

    override fun detachFragmentFoodDesigner(fragment: __FragmentFoodDesigner) {
       listener.detachFragmentFoodDesigner(fragment)
    }

    override fun detachFragmentFoodInfo(fragment: __FragmentFoodInfo) {
       listener.detachFragmentFoodInfo(fragment)
    }

    override fun onFoodAdded(food: __Food, dayIndex: Int,mealName: String) {
        listener.onFoodAddedToMeal(food,mealName,dayIndex)
    }

    override fun getDaySelectedInfo(dayIndex: Int, mealNameList: MutableList<String>) {
        //Log.d("Test","Day Index:$dayIndex")
       // Log.d("Test","Meal List")
        for(name in mealNameList){
         //   Log.d("Test",name)
        }
        this.currentDayIndex=dayIndex
        this.mealList=mealNameList
    }

    override fun onAddMeal(dayIndex: Int, mealName: String) {

        if(this.currentDayIndex==dayIndex){
            mealList.add(mealName)
        }
     //   Log.d("Test","Day Index:$dayIndex")
      //  Log.d("Test","Meal List")
        for(name in mealList){
           // Log.d("Test",name)
        }

    }

    fun updateUser(user3: __User3) {
        viewModel.setProperty(user3)
    }
}