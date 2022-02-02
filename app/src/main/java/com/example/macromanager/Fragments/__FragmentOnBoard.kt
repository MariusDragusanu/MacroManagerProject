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
import com.example.macromanager.Entity.__User3
import com.example.macromanager.Entity.__UserAccountInformation
import com.example.macromanager.Listeners.FragmentListener.__FragmentWelcomeListener
import com.example.macromanager.Listeners.FragmentListener.__FragmentAccountInformationListener
import com.example.macromanager.Listeners.FragmentListener.__FragmentDietPlanListener
import com.example.macromanager.Listeners.FragmentListener.__FragmentUserBiometricsListener
import com.example.macromanager.R
import com.example.macromanager.ViewPagers.__GenericViewPagerAdapter

import com.example.myapplication.Entity.__UserBiometrics
import com.example.myapplication.Entity.__UserDietPlan
import com.example.myapplication.Listeners.__UserRepositoryListener
import com.example.myapplication.Repository.__UserRepository2
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class __FragmentOnBoard : Fragment(),__FragmentWelcomeListener,__FragmentAccountInformationListener,__FragmentUserBiometricsListener,
    __FragmentDietPlanListener,__UserRepositoryListener {
    lateinit var viewPager2: ViewPager2
    lateinit var pagerAdapter: __GenericViewPagerAdapter
    private  var userAccountInformation: __UserAccountInformation?=null
    private  var userBiometrics: __UserBiometrics?=null
    private var userDietPlan:__UserDietPlan?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        __UserRepository2.setListener(this)
        val view = inflater.inflate(R.layout.fragment_on_board, container, false)
        viewPager2=view.findViewById(R.id.frg_onboard_ViewPager)
        val fragmentWelcome=__FragmentWelcome()
        val fragmentAccountInformation=__FragmentOnBoardAccountInformation()
        val fragmentUserBiometrics=__FragmentOnBoardBiometrics()

        fragmentAccountInformation.setListener(this)
        fragmentUserBiometrics.setListener(this)
        fragmentWelcome.setListener(this)
        val fragmentList= mutableListOf(fragmentWelcome,fragmentAccountInformation,fragmentUserBiometrics)
        pagerAdapter= __GenericViewPagerAdapter(fragmentList,requireActivity().supportFragmentManager,lifecycle)
        viewPager2.adapter=pagerAdapter
        return view
    }

    override fun onRegister() {
    viewPager2.currentItem=1
    }

    override fun onLogIn(rememberMe: Boolean) {
    }

    override fun onRetrieveAccountInformation(userAccountInformation: __UserAccountInformation) {
        this.userAccountInformation=userAccountInformation
        viewPager2.currentItem++
    }

    override fun onRequestFailed(exception: Exception) {
        Toast.makeText(requireContext(), exception.message, Toast.LENGTH_SHORT).show()
    }

    override fun onRetrieveUserBiometrics(userBiometrics: __UserBiometrics) {
        this.userBiometrics=userBiometrics
        this.userBiometrics!!.onUpdate()
        val frgDietPlan=__FragmentOnBoardDietPlan(this.userBiometrics!!)
        frgDietPlan.setListener(this)
        pagerAdapter.setSecondaryFragment(frgDietPlan)
        viewPager2.currentItem++

    }

    override fun onRetrieveDietPlan(dietPlan: __UserDietPlan) {
              this.userDietPlan=dietPlan
              val user=__User3(FirebaseAuth.getInstance().uid,this.userAccountInformation!!,this.userBiometrics!!,this.userDietPlan!!)
              __UserRepository2.getInstance()?.createUser(user)
    }

    override fun getRequestStatus(requestName: String, status: Boolean, error: String?) {
        if(requestName=="CREATE_USER"){
            if(status){
              CoroutineScope(Dispatchers.Main).launch {
                  Toast.makeText(requireContext(), "Registration Complete!", Toast.LENGTH_SHORT).show()
                  Handler(Looper.getMainLooper()).postDelayed({findNavController().navigate(R.id.action___FragmentOnBoard2_to_mainActivity2)}, 500)
              }
            }
        }
        error?.let {
            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(requireContext(),  it, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun getUser(retrievedUser: __User3) {
        TODO("Not yet implemented")
    }
}