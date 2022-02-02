package com.example.macromanager.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.macromanager.Entity.__Day
import com.example.macromanager.Entity.__Food
import com.example.macromanager.Entity.__Meal
import com.example.macromanager.Entity.__User3
import com.example.macromanager.Fragments.*
import com.example.macromanager.Listeners.FragmentListener.*
import com.example.macromanager.R
import com.example.macromanager.ViewPagers.__GenericViewPagerAdapter
import com.example.myapplication.Entity.__Date
import com.example.myapplication.Listeners.__UserRepositoryListener
import com.example.myapplication.Repository.__UserRepository2
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(),__FragmentFoodLibraryListener,__FragmentHomeListener,__UserRepositoryListener,__FragmentSettingsListener{
    private lateinit var user3: __User3
private lateinit var viewPager2: ViewPager2
private lateinit var viewPagerAdapter: __GenericViewPagerAdapter
private var dayIndex=0
    private var mealList= mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
           __UserRepository2.setListener(this)
           __UserRepository2.getInstance()?.retrieveUser(FirebaseAuth.getInstance().currentUser!!.uid)

    }
fun addFragment(fragment: Fragment){
    viewPagerAdapter.setSecondaryFragment(fragment)
    viewPager2.currentItem=3
}
    fun removeFragment(oldFragment: Fragment){
        viewPagerAdapter.removeFragment(oldFragment)
       // viewPager2.currentItem=1
    }
    fun fragmentNotNull(fragment: Fragment):Boolean{
        return !fragment.isDetached
    }

    override fun getRequestStatus(requestName: String, status: Boolean, error: String?) {
        CoroutineScope(Dispatchers.Main).launch {
            if (requestName == "UPDATE_USER") {
                if (status) {
                    Toast.makeText(this@MainActivity, "Changes Saved", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun getUser(retrievedUser: __User3) {
        CoroutineScope(Dispatchers.Main).launch {

            user3=retrievedUser

            user3.userAccountInformation.entryDate=__Date()

            if(user3.dayHistory.size==0) {
                user3.dayHistory.add(__Day())
            }
            if(user3.isANewDay()){
                if(user3.hasActiveDietPlan()){
                    Toast.makeText(this@MainActivity, "You have an active diet plan, keep going!", Toast.LENGTH_SHORT).show()
                }
                user3.addLastMealToHistory()
            }
            viewPager2 = findViewById(R.id.main_viewPager)
            val frgHome = __FragmentMainHome(user3)
            frgHome.setListener(this@MainActivity)
            val frgLibrary=__FragmentMainLibrary(user3)
            frgLibrary.setListener(this@MainActivity)
            frgHome.setComm(frgLibrary)
            val frgSettings=__FragmentMainSettings(user3)
            frgSettings.setListener(this@MainActivity)
            viewPagerAdapter= __GenericViewPagerAdapter(mutableListOf(frgLibrary,frgHome,frgSettings),this@MainActivity.supportFragmentManager,lifecycle)
            viewPager2.adapter=viewPagerAdapter
        }
    }

    override fun attachFragmentFoodDesigner() {
        val frgDesigner=__FragmentFoodDesigner()
        frgDesigner.setListener(viewPagerAdapter.fragmentList[0] as __FragmentMainLibrary)
        addFragment(frgDesigner)
    }

    override fun detachFragmentFoodDesigner(fragment: __FragmentFoodDesigner) {
        removeFragment(fragment)
        viewPager2.currentItem=0
    }

    override fun attachFragmentFoodInfo(currentFood: __Food,dayIndex: Int, mealList: MutableList<String>) {
        val frg=__FragmentFoodInfo(currentFood,mealList,dayIndex)
        this.dayIndex=dayIndex
        this.mealList=mealList
        frg.setListener(viewPagerAdapter.fragmentList[0] as __FragmentMainLibrary)
        addFragment(frg)
    }

    override fun onFoodAddedToMeal(currentFood: __Food, mealName: String,dayIndex: Int) {
        (viewPagerAdapter.fragmentList[1] as __FragmentMainHome).onAddFoodToMeal(currentFood,mealName,dayIndex)
    }

    override fun onFoodCreated() {

    }

    override fun detachFragmentFoodInfo(fragment: __FragmentFoodInfo) {
       removeFragment(fragment)
        viewPager2.currentItem=0
    }

    override fun attachFragmentMealInfo(currentMeal: __Meal,dayIndex: Int) {
        val frg=__FragmentMealInfo(currentMeal,dayIndex)
        frg.setListener(viewPagerAdapter.fragmentList[1] as __FragmentMainHome)
        addFragment(frg)
    }

    override fun detachFragmentMealInfo(fragment: __FragmentMealInfo) {
        removeFragment(fragment)
        viewPager2.currentItem=1
    }

    override fun onAddFoodToMeal(fragment: __FragmentMealInfo,dayIndex: Int,mealName: String) {
        removeFragment(fragment)
        this.dayIndex=dayIndex
        this.mealList=user3.dayHistory[dayIndex].createMealNameList()
     viewPager2.currentItem=0
    }

    override fun updateCurrentUser(user3: __User3) {
        this.user3=user3
        this.user3.userAccountInformation.exitDate=__Date()
        __UserRepository2.getInstance()?.updateUser(this.user3)
        (viewPagerAdapter.fragmentList[0] as __FragmentMainLibrary) .updateUser(user3)
        (viewPagerAdapter.fragmentList[1] as __FragmentMainHome).updateUser(user3)
        (viewPagerAdapter.fragmentList[2] as __FragmentMainSettings ).updateUser(user3)

    }

    override fun attachFragmentFoodInfo(currentFood: __Food,currentMeal:String,dayIndex: Int) {
        this.dayIndex=dayIndex

        val frg=__FragmentFoodInfo(currentFood,mutableListOf(currentMeal),dayIndex)
        frg.setListener(viewPagerAdapter.fragmentList[0] as __FragmentMainLibrary)
        addFragment(frg)
    }

    override fun detachFragmentSettingsBiometrics(
        fragment: __FragmentSettingsBiometrics,
        currentUser3: __User3?
    ) {
        removeFragment(fragment)
        viewPager2.currentItem=2
        if(currentUser3!=null){
            user3=currentUser3
            Toast.makeText(this, "Weight Updated!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun attachFragmentSettingsBiometrics() {
        val frg=__FragmentSettingsBiometrics(user3)
        frg.setListener(viewPagerAdapter.fragmentList[2] as __FragmentMainSettings)
        addFragment(frg)
    }


}