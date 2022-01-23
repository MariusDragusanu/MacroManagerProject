package com.example.macromanager.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.example.macromanager.Entity.__Food
import com.example.macromanager.Entity.__Meal
import com.example.macromanager.Entity.__User3
import com.example.macromanager.Listeners.FragmentListener.__FragmentFoodLibraryListener
import com.example.macromanager.Listeners.FragmentListener.__FragmentHomeListener
import com.example.macromanager.Listeners.FragmentListener.__FragmentMainListener
import com.example.macromanager.R
import com.example.macromanager.ViewPagers.__GenericViewPagerAdapter
import com.example.myapplication.Listeners.__UserRepositoryListener
import com.example.myapplication.Repository.__UserRepository2
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class __FragmentMain : Fragment(),__UserRepositoryListener, __FragmentFoodLibraryListener,
    __FragmentHomeListener {
    private lateinit var listener: __FragmentMainListener
    fun setListener(newListener: __FragmentMainListener){
        this.listener=newListener
    }
  private lateinit var viewPager: ViewPager2
  private lateinit var viewPagerAdapter: __GenericViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        __UserRepository2.setListener(this)
        __UserRepository2.getInstance()?.retrieveUser(FirebaseAuth.getInstance().currentUser!!.uid)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun getRequestStatus(requestName: String, status: Boolean, error: String?) {
        CoroutineScope(Dispatchers.Main).launch {
            error?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun getUser(retrievedUser: __User3) {
        CoroutineScope(Dispatchers.Main).launch {
            viewPager=requireView().findViewById(R.id.frg_main_viewPager)
            val frgLibrary=__FragmentFoodLibrary(retrievedUser)
            val frgHome=__FragmentHome(retrievedUser,)
            val frgSettings=__FragmentAccount()
            frgLibrary.setListener(this@__FragmentMain)
            frgHome.setListener(this@__FragmentMain)
            //frgSettings.setListener(this)
            viewPagerAdapter= __GenericViewPagerAdapter(mutableListOf(frgLibrary,frgHome,frgSettings),requireActivity().supportFragmentManager,lifecycle)
            viewPager.adapter=viewPagerAdapter
        }


    }

    override fun attachFragmentFoodDesigner() {
        listener.attachFragmentFoodDesigner()
    }

    override fun detachFragmentFoodDesigner(fragment: __FragmentFoodDesigner) {
        listener.detachFragmentFoodDesigner(fragment)
    }

    override fun attachFragmentFoodInfo(currentFood: __Food,mealTitleList: MutableList<String>) {

        listener.attachFragmentFoodInfo(currentFood,mealTitleList)
    }

    override fun attachFragmentMealInfo(currentMeal: __Meal) {
        listener.attachFragmentMealInfo(currentMeal)
    }
    fun setViewPagerFragment(position:Int){
        viewPager.currentItem=position
    }


}