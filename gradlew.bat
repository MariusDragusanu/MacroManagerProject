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
import com.example.macromanager.Listeners.__FragmentWelcomeListener
import com.example.macromanager.Listeners.__FragmentAccountInformationListener
import com.example.macromanager.Listeners.__FragmentUserBiometricsListener
import com.example.macromanager.R
import com.example.macromanager.ViewPagers.__GenericViewPagerAdapter
import com.example.myapplication.Entity.__User
import com.example.myapplication.Entity.__UserBiometrics
import com.example.myapplication.Listeners.__UserRepositoryListener
import com.example.myapplication.Repository.__UserRepository2
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class __FragmentOnBoard : Fragment(), __FragmentWelcomeListener,__Register1Listener,__Register2Listener,__UserRepositoryListener {
    lateinit var viewPager2: ViewPager2
    lateinit var pagerAdapter: __GenericViewPagerAdapter
    private lateinit var user:__User2
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
        user= __User2(firebaseUser)
        Toast.makeText(requireContext(), firebaseUser?.email, Toast.LENGTH_SHORT).show()
        viewPager2.currentItem=1
    }

    override fun getBiometrics(userBiometrics: __UserBiometrics) {
       user.setUserBiometrics(userBiometrics)
    }

    override fun hasFinishedRegistration(state: Boolean) {
        if(state){
           __UserRepository2.getInstance()?.createUser(user = user)
        }
        else{
             viewPager2.currentItem=3
        }
    }

    ove