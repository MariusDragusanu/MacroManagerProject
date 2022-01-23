package com.example.macromanager.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.macromanager.Adapters.__FoodAdapterForLibrary
import com.example.macromanager.Entity.__Food
import com.example.macromanager.Entity.__User3
import com.example.macromanager.R
import com.example.macromanager.ViewModel.__UserViewModel
import com.example.macromanager.ViewModelFactory.__UserViewModelFactory
import com.example.myapplication.Listeners.__FoodRepositoryListener
import com.example.myapplication.Repository.__FoodRepository2
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class __FragmentFoodLibrary(val currentUser:__User3):Fragment(R.layout.fragment_main_food_library),__FoodRepositoryListener {
    private lateinit var ilSearch:TextInputLayout
    private lateinit var etSearch:TextInputEditText
    private lateinit var pbSearch:ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter:__FoodAdapterForLibrary
    private lateinit var viewModel:__UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_food_library, container, false)
        __FoodRepository2.setListener(this)
        ilSearch = view.findViewById(R.id.frg_library_ilSearch)
        etSearch = view.findViewById(R.id.frg_library_etSearch)
        recyclerView = view.findViewById(R.id.frg_library_rvfood)
        viewModel = ViewModelProvider(
            this,
            __UserViewModelFactory(currentUser)
        )[__UserViewModel::class.java]
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = __FoodAdapterForLibrary(mutableListOf(), R.layout.entity_food_layout)
        recyclerView.adapter = adapter
        pbSearch = view.findViewById(R.id.frg_library_pbSearch)
        etSearch.doOnTextChanged { text, start, before, count ->
            if (etSearch.text.toString().isNotEmpty()) {
                pbSearch.visibility = VISIBLE
                __FoodRepository2.getInstance()?.queryFood(etSearch.text.toString())
            }
        }


        return view
    }

    override fun getRequestStatus(requestName: String, status: Boolean, error: String?) {
       if(requestName=="QUERY_FOOD"){
           if(status){
               pbSearch.visibility=INVISIBLE
           }
       }
    }

    override fun getFoodQuery(query: MutableList<__Food>) {
        adapter.setListToBeDiplayed(query)
    }
}