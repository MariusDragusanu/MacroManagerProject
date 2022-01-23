package com.example.macromanager.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.macromanager.Entity.__Food
import com.example.macromanager.Entity.__Meal
import com.example.macromanager.Fragments.*
import com.example.macromanager.Listeners.FragmentListener.__FragmentFoodDesignerListener
import com.example.macromanager.Listeners.FragmentListener.__FragmentFoodInfoListener
import com.example.macromanager.Listeners.FragmentListener.__FragmentMainListener
import com.example.macromanager.Listeners.FragmentListener.__FragmentMealInfoListener
import com.example.macromanager.R

class MainActivity : AppCompatActivity(), __FragmentMainListener, __FragmentFoodDesignerListener,
    __FragmentFoodInfoListener,
    __FragmentMealInfoListener {
private lateinit var fragmentMain: __FragmentMain
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         fragmentMain= __FragmentMain()
        fragmentMain.setListener(this)
         addFragment(fragmentMain)

    }
fun addFragment(fragment: Fragment){
    val transaction=supportFragmentManager.beginTransaction()
    transaction.add(R.id.main_frLayout,fragment)
    transaction.commit()
}
    fun removeFragment(oldFragment: Fragment){
        val transaction=supportFragmentManager.beginTransaction()
        transaction.remove(oldFragment)
        transaction.commit()
    }
    fun fragmentNotNull(fragment: Fragment):Boolean{
        return !fragment.isDetached
    }

    override fun attachFragmentFoodDesigner() {
        val frgFoodDesigner=__FragmentFoodDesigner()
        frgFoodDesigner.setListener(this)
        addFragment(frgFoodDesigner)
    }

    override fun detachFragmentFoodDesigner(fragment: __FragmentFoodDesigner) {
        removeFragment(fragment)


    }

    override fun attachFragmentMealInfo(currentMeal: __Meal) {
        val frgMeal=__FragmentMealInfo(currentMeal)
        frgMeal.setListener(this)
        addFragment(frgMeal)
    }

    override fun attachFragmentFoodInfo(currentFood: __Food,mealList:MutableList<String>) {
        val frgFoodInfo=__FragmentFoodInfo(currentFood,mealList)
        frgFoodInfo.setListener(this)
        addFragment(frgFoodInfo)
    }

    override fun detachFragmentFoodInfo(fragment: __FragmentFoodInfo) {
        removeFragment(fragment)
    }

    override fun onFoodAdded(food: __Food, meal: __Meal) {

    }

    override fun detachFragmentMealInfo(fragment: __FragmentMealInfo) {
        removeFragment(fragment)
    }

    override fun onAddFood(fragment:__FragmentMealInfo) {
        removeFragment(fragment)
        fragmentMain.setViewPagerFragment(0)

    }


}