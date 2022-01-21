package com.example.macromanager.Fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.macromanager.Entity.__User2
import com.example.macromanager.Entity.__UserAccountInformation
import com.example.macromanager.Listeners.__FragmentDietPlanListener
import com.example.macromanager.Listeners.__FragmentWelcomeListener
import com.example.macromanager.Listeners.__Register1Listener
import com.example.macromanager.Listeners.__Register2Listener
import com.example.macromanager.R
import com.example.macromanager.ViewPagers.__GenericViewPagerAdapter

import com.example.myapplication.Entity.__UserBiometrics
import com.example.myapplication.Entity.__UserDietPlan
import com.example.myapplication.Listeners.__UserRepositoryListener
import com.example.myapplication.Repository.__UserRepository2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class __FragmentOnBoard : Fragment(), __FragmentWelcomeListener,__Register1Listener,__Register2Listener,__UserRepositoryListener,__FragmentDietPlanListener {
    lateinit var viewPager2: ViewPager2
    lateinit var pagerAdapter: __GenericViewPagerAdapter
    private  var userAccountInformation: __UserAccountInformation?=null
    private  var userBiometrics: __UserBiometrics?=null
    private var userDietPlan:__UserDietPlan?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_on_board, container, false)
        __UserRepository2.setListener(this)
        viewPager2=view.findViewById(R.id.frg_onboard_ViewPager)
        val frgWelcome=__FragmentWelcome()
        val frgReg1=__FragmentRegister1()
        val frgReg2=__FragmentRegister2()
        frgWelcome.setListener(this)
        frgReg1.setListener(this)
        frgReg2.setListener(this)
        val fragmentList= mutableListOf(frgWelcome,frgReg1,frgReg2)
        pagerAdapter= __GenericViewPagerAdapter(fragmentList,requireActivity().supportFragmentManager,lifecycle)
        viewPager2.adapter=pagerAdapter
        return view
    }

    override fun getNewAccount(firebaseUser: FirebaseUser?) {
        userAccountInformation= __UserAccountInformation(firebaseUser)


        viewPager2.currentItem=1
    }

    override fun getBiometrics(newuserBiometrics: __UserBiometrics) {
       userBiometrics=newuserBiometrics
    }

    override fun decidedForMealPlan() {
        val frg = __FragmentDietPlan(userBiometrics!!)
        frg.setListener(this)
        (viewPager2.adapter as __GenericViewPagerAdapter).addFragment(frg)
        viewPager2.currentItem = 3
    }

    override fun hasFinishedRegistration() {
        if (userAccountInformation != null) {
            val user = __User2(FirebaseAuth.getInstance().currentUser)
            user.setAccountInformation(userAccountInformation!!)
            if (userBiometrics != null) {
                userBiometrics!!.onUpdate()
                user.setUserBiometrics(userBiometrics!!)
                if (userDietPlan != null) {
                    userDietPlan!!.onUserInfoChanged(
                        userBiometrics!!.getBaseMetabolicRate()!!,
                        userBiometrics!!.getActivityLevelCoefficient()!!
                    )
                    user.setDietPlan(userDietPlan!!)

                } else {
                    userDietPlan = __UserDietPlan(0.3f, 0.25f, 0.45f)
                    userDietPlan!!.onUserInfoChanged(
                        userBiometrics!!.getActivityLevelCoefficient()!!,
                        userBiometrics!!.getActivityLevelCoefficient()!!
                    )
                }

            }
            __UserRepository2.getInstance()!!.createUser(user)

        }
    }


    override fun getRequestStatus(requestName: String, status: Boolean, error: String?) {
        if(requestName=="CREATE_USER"){
            if(status){

                Handler(Looper.getMainLooper()).postDelayed({findNavController().navigate(R.id.action___FragmentOnBoard2_to_mainActivity2)}, 500)
            }else{
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
                }
            }
        }
        if(requestName=="UPDATE_USER"){
            if(status){
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(requireContext(), "Diet Plan saved!", Toast.LENGTH_LONG).show()
                }
                Handler(Looper.getMainLooper()).postDelayed({findNavController().navigate(R.id.action___FragmentOnBoard2_to_mainActivity2)}, 500)
            }
            else{
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun getUser(retrievedUser: __User2) {

    }

    override fun onRegister() {
        viewPager2.currentItem=1
    }

    override fun onLogIn(rememberMe:Boolean) {
        Handler(Looper.getMainLooper()).postDelayed({findNavController().navigate(R.id.action___FragmentOnBoard2_to_mainActivity2)}, 500)
    }

    override fun getDietPlan(dietPlan: __UserDietPlan) {
        userDietPlan = dietPlan
        if (userAccountInformation != null) {
            val user = __User2(FirebaseAuth.getInstance().currentUser)
            user.setAccountInformation(userAccountInformation!!)
            if (userBiometrics != null) {
                userBiometrics!!.onUpdate()
                user.setUserBiometrics(userBiometrics!!)
                if (userDietPlan != null) {
                    userDietPlan!!.onUserInfoChanged(
                        userBiometrics!!.getBaseMetabolicRate()!!,
                        userBiometrics!!.getActivityLevelCoefficient()!!
                    )
                    user.setDietPlan(userDietPlan!!)

                } else {
                    userDietPlan = __UserDietPlan(0.3f, 0.25f, 0.45f)
                    userDietPlan!!.onUserInfoChanged(
                        userBiometrics!!.getActivityLevelCoefficient()!!,
                        userBiometrics!!.getActivityLevelCoefficient()!!
                    )
                }

            }
            __UserRepository2.getInstance()!!.createUser(user)

        }


    }
}