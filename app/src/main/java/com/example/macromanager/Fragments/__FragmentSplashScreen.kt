package com.example.macromanager.Fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.macromanager.R
import com.google.firebase.auth.FirebaseAuth

class __FragmentSplashScreen:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       FirebaseAuth.getInstance().signOut()
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onStart() {
        super.onStart()
        val currentUser=FirebaseAuth.getInstance().currentUser
        if(currentUser==null){
            Handler(Looper.getMainLooper()).postDelayed({findNavController().navigate(R.id.action___FragmentSplashScreen2_to___FragmentOnBoard2)}, 500)
        }else{

            Handler(Looper.getMainLooper()).postDelayed({findNavController().navigate(R.id.action___FragmentSplashScreen2_to_mainActivity2)}, 500)
        }
    }
}